# poc-structured-concurrency

## 1. Objective

To demonstrate Structured Concurrency in a real-world scenario.

Suggested architecture

````
fraud-analysis-poc
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