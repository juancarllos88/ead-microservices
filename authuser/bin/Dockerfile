#FROM amazoncorretto:11-alpine-jdk
FROM adoptopenjdk/openjdk11:latest 
COPY target/authuser-0.0.1-SNAPSHOT.jar authuser.jar
ENTRYPOINT [ "java", "-jar", "/authuser.jar" ]