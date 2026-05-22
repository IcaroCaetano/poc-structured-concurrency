package com.myproject.poc_structured_concurrency.dto;// dto/BureauResponse.java

public record BureauResponse(
        int score,
        boolean approved
) { }