package com.myproject.poc_structured_concurrency.service;

import com.myproject.poc_structured_concurrency.dto.FaceMatchResponse;
import com.myproject.poc_structured_concurrency.util.LoggerUtil;

import java.time.Duration;

public class FaceMatchService {

    public FaceMatchResponse analyze(String cpf) {

        try {

            LoggerUtil.log("Starting Face Match");

            Thread.sleep(Duration.ofSeconds(1));

            LoggerUtil.log("Finishing Face Match");

            return new FaceMatchResponse(true, 0.98);

        } catch (InterruptedException e) {

            LoggerUtil.log("Face Match CANCELLED");

            throw new RuntimeException(e);
        }
    }
}