FROM amazoncorretto:21-alpine-jdk
COPY target/*.jar pandev.jar
ENTRYPOINT ["java","-jar","/pandev.jar"]