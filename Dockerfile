FROM openjdk:17-jdk

WORKDIR /cinepick

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "cinepick.jar"]
