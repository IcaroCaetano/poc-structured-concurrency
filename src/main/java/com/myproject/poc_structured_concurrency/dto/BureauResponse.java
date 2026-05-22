package com.myproject.poc_structured_concurrency.dto;

public record BureauResponse(
        int score,
        boolean approved
) { }