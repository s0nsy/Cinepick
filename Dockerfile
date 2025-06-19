FROM openjdk:17-jdk

WORKDIR /cinepick
COPY cinepick.jar cinepick.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "cinepick.jar"]
