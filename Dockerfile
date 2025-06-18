FROM amazoncorretto:17

LABEL authors="2or5"

WORKDIR /application

COPY target/football-manager-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]