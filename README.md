#Tree Match API
##Docker Build And Run
1. go to project level where the Dockerfile resides 
2. $docker build -t [name:tag] .
3. $docker run -p8080:8080 -d [name:tag]

##Build Spec
1. OpenJDK Java 8
2. Apache-maven-3.6.3

##Note For Alternative Solution
Building the entire API and create a GraphQL schema for the question json file by using AWS SAM model with serverless function written in java, and deploy directly to AWS Cloud




