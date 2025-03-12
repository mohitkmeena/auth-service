FROM openjdk:21
COPY target/authservice.jar authservice.jar
ENTRYPOINT ["java" ,"-jar","/authservice.jar"]