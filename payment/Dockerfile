#FROM amazoncorretto:11-alpine-jdk
FROM adoptopenjdk/openjdk11:latest
COPY target/payment-0.0.1-SNAPSHOT.jar payment.jar
ENTRYPOINT [ "java", "-jar", "/payment.jar" ]