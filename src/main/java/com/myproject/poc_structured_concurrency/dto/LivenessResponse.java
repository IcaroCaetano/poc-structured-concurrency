package com.myproject.poc_structured_concurrency.dto;

public record LivenessResponse(
        boolean alive,
        double confidence
) { }