# poc-structured-concurrency

## 1. Objective

To demonstrate Structured Concurrency in a real-world scenario.

Suggested architecture

````
fraud-analysis-poc
│
├── controller
│   └── FraudController
│
├── service
│   ├── FraudAnalysisService
│   ├── FaceMatchService
│   ├── LivenessService
│   └── BureauService
│
├── dto
│
├── config
│
└── util
````