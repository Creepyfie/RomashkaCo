FROM openjdk:17
MAINTAINER Krutov Anton <fynjybjyb@mail.ru>
COPY target/RomashkaKo-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]