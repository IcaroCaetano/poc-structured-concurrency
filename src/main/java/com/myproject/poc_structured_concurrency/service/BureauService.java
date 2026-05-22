package com.myproject.poc_structured_concurrency.service;

import com.myproject.poc_structured_concurrency.dto.BureauResponse;
import com.myproject.poc_structured_concurrency.util.LoggerUtil;

import java.time.Duration;

public class BureauService {

    public BureauResponse analyze(String cpf) {

        try {

            LoggerUtil.log("Starting Bureau");

            Thread.sleep(Duration.ofSeconds(3));

            LoggerUtil.log("Finishing Bureau");

            return new BureauResponse(850, true);

        } catch (InterruptedException e) {

            LoggerUtil.log("Bureau CANCELLED");

            throw new RuntimeException(e);
        }
    }
}