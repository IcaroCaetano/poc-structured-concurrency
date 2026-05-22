# poc-structured-concurrency

## 1. Objective

To demonstrate Structured Concurrency in a real-world scenario.

#### Suggested architecture

````
poc-structured-concurrency
│
├── controller
│   └── FraudController
│
├── service
│   ├── FraudAnalysisService
│   ├── FaceMatchService
│   ├── LivenessService
│   └── BureauService
│
├── dto
│
├── config
│
└── util
````


The application performs multiple external integrations in parallel:

- Face Match
- Liveness
- Bureau Score


Everything coordinated through:

- StructuredTaskScope
- Virtual Threads
- Scoped Values

Business scenario

In real-world anti-fraud systems, it is common for a single analysis to need to consult several external providers simultaneously.

Example:

````
Fraud Analysis
    |
    +--> Face Match Provider
    +--> Liveness Provider
    +--> Bureau Score Provider
````

Each integration:

- has its own latency,
- can fail,
- can timeout,
- executes independently


## Application Flow

### 1. The application starts in Main

The Main class:

- generates a requestId,
- propagates the context using ScopedValue,
- starts the anti-fraud analysis.


````java
public class PocStructuredConcurrencyApplication {

	 static void main(String[] args) {

		var requestId = UUID.randomUUID().toString();

		ScopedValue.where(RequestContext.REQUEST_ID, requestId)
				.run(() -> {

					var fraudAnalysisService = new FraudAnalysisService();

					var response = fraudAnalysisService.analyze("12345678900");

					System.out.println("\nFINAL RESPONSE");
					System.out.println(response);
				});
	}
}
````

### 2. The FraudAnalysisService initiates the main flow

The FraudAnalysisService is responsible for:

- coordinating all external integrations,
- opening the structured scope,
- creating concurrent subtasks,
- awaiting results.

````java
public class FraudAnalysisService {

    private final FaceMatchService faceMatchService = new FaceMatchService();

    private final LivenessService livenessService = new LivenessService();

    private final BureauService bureauService = new BureauService();

    public FraudAnalysisResponse analyze(String cpf) {

        LoggerUtil.log("Starting Fraud Analysis");

        try (var scope = StructuredTaskScope.open()) {

            var faceTask = scope.fork(() -> faceMatchService.analyze(cpf));

            var livenessTask = scope.fork(() -> livenessService.analyze(cpf));

            var bureauTask = scope.fork(() -> bureauService.analyze(cpf));

            scope.join();

            LoggerUtil.log("All services finished successfully");

            return new FraudAnalysisResponse(
                    faceTask.get(),
                    livenessTask.get(),
                    bureauTask.get()
            );

        } catch (Exception e) {

            LoggerUtil.log("Fraud Analysis FAILED");

            throw new RuntimeException(e);
        }
    }
}
````
### 3. The StructuredTaskScope creates the concurrent scope.

````
try (var scope = StructuredTaskScope.open()) {
````
#### At this point:

- a structured scope is created,
- all child tasks become part of the same context,
- the task lifecycle is automatically controlled.


### 4. Each service is executed concurrently with fork()

The application creates three subtasks:

````java
var faceTask = scope.fork(() -> faceMatchService.analyze(cpf));

var livenessTask = scope.fork(() -> livenessService.analyze(cpf));

var bureauTask = scope.fork(() -> bureauService.analyze(cpf));
````

#### Each `fork()`:

- creates a Virtual Thread,
- registers the task in the parent scope,
- starts concurrent execution.



### 5. The join() function waits for subtasks.

````
scope.join();
````

#### The main flow:

- pause,
- wait for all subtasks to finish,
- maintain centralized control of processing.

### 6. The results are aggregated

After all tasks are completed:

````
return new FraudAnalysisResponse(
        faceTask.get(),
        livenessTask.get(),
        bureauTask.get()
);
````




````
[requestId=c305f92b-14b8-41a1-9ba3-8be2ea97ea4b] [thread=Thread[#3,main,5,main]] Starting Fraud Analysis
		[requestId=c305f92b-14b8-41a1-9ba3-8be2ea97ea4b] [thread=VirtualThread[#30]/runnable@ForkJoinPool-1-worker-1] Starting Face Match
		[requestId=c305f92b-14b8-41a1-9ba3-8be2ea97ea4b] [thread=VirtualThread[#35]/runnable@ForkJoinPool-1-worker-2] Starting Liveness
		[requestId=c305f92b-14b8-41a1-9ba3-8be2ea97ea4b] [thread=VirtualThread[#40]/runnable@ForkJoinPool-1-worker-6] Starting Bureau
		[requestId=c305f92b-14b8-41a1-9ba3-8be2ea97ea4b] [thread=VirtualThread[#30]/runnable@ForkJoinPool-1-worker-2] Finishing Face Match
		[requestId=c305f92b-14b8-41a1-9ba3-8be2ea97ea4b] [thread=VirtualThread[#35]/runnable@ForkJoinPool-1-worker-2] Finishing Liveness
		[requestId=c305f92b-14b8-41a1-9ba3-8be2ea97ea4b] [thread=VirtualThread[#40]/runnable@ForkJoinPool-1-worker-2] Finishing Bureau
		[requestId=c305f92b-14b8-41a1-9ba3-8be2ea97ea4b] [thread=Thread[#3,main,5,main]] All services finished successfully

		FINAL RESPONSE
		FraudAnalysisResponse[faceMatch=FaceMatchResponse[matched=true, score=0.98], liveness=LivenessResponse[alive=true, confidence=0.99], bureau=BureauResponse[score=850, approved=true]]
````