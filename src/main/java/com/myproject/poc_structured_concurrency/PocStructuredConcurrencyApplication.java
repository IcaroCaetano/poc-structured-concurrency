package com.myproject.poc_structured_concurrency;

import com.myproject.poc_structured_concurrency.context.RequestContext;
import com.myproject.poc_structured_concurrency.service.FraudAnalysisService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
public class PocStructuredConcurrencyApplication {

	public static void main(String[] args) {

		var requestId = UUID.randomUUID().toString();

		ScopedValue.where(RequestContext.REQUEST_ID, requestId)
				.run(() -> {

					var fraudAnalysisService = new FraudAnalysisService();

					var response = fraudAnalysisService.analyze("12345678900");

					System.out.println("\nFINAL RESPONSE");
					System.out.println(response);
				});
	}

	/*
	 * 	[requestId=c305f92b-14b8-41a1-9ba3-8be2ea97ea4b] [thread=Thread[#3,main,5,main]] Starting Fraud Analysis
		[requestId=c305f92b-14b8-41a1-9ba3-8be2ea97ea4b] [thread=VirtualThread[#30]/runnable@ForkJoinPool-1-worker-1] Starting Face Match
		[requestId=c305f92b-14b8-41a1-9ba3-8be2ea97ea4b] [thread=VirtualThread[#35]/runnable@ForkJoinPool-1-worker-2] Starting Liveness
		[requestId=c305f92b-14b8-41a1-9ba3-8be2ea97ea4b] [thread=VirtualThread[#40]/runnable@ForkJoinPool-1-worker-6] Starting Bureau
		[requestId=c305f92b-14b8-41a1-9ba3-8be2ea97ea4b] [thread=VirtualThread[#30]/runnable@ForkJoinPool-1-worker-2] Finishing Face Match
		[requestId=c305f92b-14b8-41a1-9ba3-8be2ea97ea4b] [thread=VirtualThread[#35]/runnable@ForkJoinPool-1-worker-2] Finishing Liveness
		[requestId=c305f92b-14b8-41a1-9ba3-8be2ea97ea4b] [thread=VirtualThread[#40]/runnable@ForkJoinPool-1-worker-2] Finishing Bureau
		[requestId=c305f92b-14b8-41a1-9ba3-8be2ea97ea4b] [thread=Thread[#3,main,5,main]] All services finished successfully

		FINAL RESPONSE
		FraudAnalysisResponse[faceMatch=FaceMatchResponse[matched=true, score=0.98], liveness=LivenessResponse[alive=true, confidence=0.99], bureau=BureauResponse[score=850, approved=true]]

	 */

}
