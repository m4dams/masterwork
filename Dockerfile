FROM openjdk:8-jdk-alpine

WORKDIR /home/temp
COPY ./build/libs/library-0.0.1-SNAPSHOT.jar ./app.jar
CMD ["java", "-jar", "app.jar"]