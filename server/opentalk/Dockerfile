#Package stage
FROM openjdk:17-jdk-slim

RUN mkdir /app
WORKDIR /app

COPY target/opentalk-0.0.1-SNAPSHOT.jar /app/
EXPOSE 8080

CMD ["java", "-Duser.timezone=GMT+7", "-Djava.security.egd=file:/dev/./urandom", "-jar", "opentalk-0.0.1-SNAPSHOT.jar"]