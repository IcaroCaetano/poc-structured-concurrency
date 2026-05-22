package com.myproject.poc_structured_concurrency.dto;// dto/FaceMatchResponse.java

public record FaceMatchResponse(
        boolean matched,
        double score
) { }