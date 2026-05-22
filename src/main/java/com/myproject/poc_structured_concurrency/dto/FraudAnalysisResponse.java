package com.myproject.poc_structured_concurrency.dto;// dto/FraudAnalysisResponse.java

public record FraudAnalysisResponse(
        FaceMatchResponse faceMatch,
        LivenessResponse liveness,
        BureauResponse bureau
) { }