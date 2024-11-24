# Usa una imagen base con OpenJDK 17
FROM openjdk:17-jdk-slim AS build

RUN apt-get update && apt-get install -y maven

COPY . /app

WORKDIR /app

RUN mvn clean install

FROM openjdk:17-jdk-slim

COPY --from=build /app/target/core-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
