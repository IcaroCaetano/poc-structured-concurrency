package com.myproject.poc_structured_concurrency.dto;

public record FaceMatchResponse(
        boolean matched,
        double score
) { }