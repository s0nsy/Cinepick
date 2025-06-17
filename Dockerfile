FROM openjdk:17-jdk

WORKDIR /cinepick

COPY build/libs/cinepick_BE-0.0.1-SNAPSHOT.jar cinepick.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "cinepick.jar"]
