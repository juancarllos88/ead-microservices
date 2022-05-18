#FROM amazoncorretto:11-alpine-jdk
FROM adoptopenjdk/openjdk11:latest 
COPY target/api-gateway-0.0.1-SNAPSHOT.jar api-gateway.jar
ENTRYPOINT [ "java", "-jar", "/api-gateway.jar" ]