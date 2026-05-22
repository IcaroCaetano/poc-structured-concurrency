package com.myproject.poc_structured_concurrency.dto;

public record FraudAnalysisResponse(
        FaceMatchResponse faceMatch,
        LivenessResponse liveness,
        BureauResponse bureau
) { }