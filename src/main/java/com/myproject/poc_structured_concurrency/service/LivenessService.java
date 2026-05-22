package com.myproject.poc_structured_concurrency.service;

import com.myproject.poc_structured_concurrency.dto.LivenessResponse;
import com.myproject.poc_structured_concurrency.util.LoggerUtil;

import java.time.Duration;

public class LivenessService {

    public LivenessResponse analyze(String cpf) {

        try {

            LoggerUtil.log("Starting Liveness");

            Thread.sleep(Duration.ofSeconds(2));

            LoggerUtil.log("Finishing Liveness");

            return new LivenessResponse(true, 0.99);

        } catch (InterruptedException e) {

            LoggerUtil.log("Liveness CANCELLED");

            throw new RuntimeException(e);
        }
    }
}