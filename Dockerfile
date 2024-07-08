FROM openjdk:17-alpine
MAINTAINER Krutov Anton <fynjybjyb@mail.ru>
COPY target/romashka-co-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]