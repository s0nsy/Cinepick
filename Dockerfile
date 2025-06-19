FROM openjdk:17-jdk

WORKDIR /cinepick

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "cinepick.jar"]
