FROM eclipse-temurin:23-jdk
VOLUME /tmp
EXPOSE 8080
ADD build/libs/rent-0.0.1-SNAPSHOT-plain.jar app.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]