FROM maven:3.6.3-jdk-11-slim AS MAVEN_BUILD
COPY . .
RUN mvn package

FROM openjdk:8-jre-slim
COPY --from=MAVEN_BUILD /target/treematch-0.0.1-SNAPSHOT.jar .
COPY --from=MAVEN_BUILD questions.json .
CMD ["java", "-jar", "treematch-0.0.1-SNAPSHOT.jar" ]