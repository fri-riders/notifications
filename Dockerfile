FROM openjdk:8-jre-alpine
EXPOSE 8081
ENV CONFIG_SERVER_URL consul
ENV DISCOVERY_HOSTNAME notifications
ADD target/notifications-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]