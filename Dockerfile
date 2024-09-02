FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/fileService-0.0.1-SNAPSHOT.jar /app/caselabjava.jar
ENTRYPOINT ["java", "-jar", "/app/caselabjava.jar"]
