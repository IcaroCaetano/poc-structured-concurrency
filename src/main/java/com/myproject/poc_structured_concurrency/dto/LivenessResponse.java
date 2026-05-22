package com.myproject.poc_structured_concurrency.dto;// dto/LivenessResponse.java

public record LivenessResponse(
        boolean alive,
        double confidence
) { }