#FROM amazoncorretto:11-alpine-jdk
FROM adoptopenjdk/openjdk11:latest 
COPY target/service-registry-0.0.1-SNAPSHOT.jar service-registry.jar
ENTRYPOINT [ "java", "-jar", "/service-registry.jar" ]