# Tree Match API
## Docker Build 
1. go to project level where the Dockerfile resides 
2. $docker build -t treematch:1.0 .
3. $docker run -p8080:8080 -d treematch:1.0

## Run And Access API
Access API from localhost:8080 \
/GET /api/begin \
/POST /api/answer 

## Build Spec
1. OpenJDK Java 8
2. Apache-maven-3.6.3

## Note For Alternative Solution
Building the entire API and create a GraphQL schema for the question json file by using AWS SAM model with serverless function written in java, and deploy directly to AWS Cloud




