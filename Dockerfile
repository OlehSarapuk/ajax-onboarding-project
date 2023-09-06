FROM ubuntu:20.04
RUN apt-get update && apt-get install -y protobuf-compiler

FROM openjdk:17-jdk-buster
COPY . .
RUN apt-get update && apt-get install -y protobuf-compiler
ARG JAR_FILE=build/libs/ajax-onboarding-project-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
CMD ["./gradlew", "build"]
ENTRYPOINT ["java", "-jar", "app.jar"]
