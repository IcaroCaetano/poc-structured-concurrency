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

}
