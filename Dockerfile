FROM openjdk:11-jre-slim-buster
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Dspring.config.location=/var/lib/jenkins/workspace/jupjupserver/src/main/resources/key.yml" ,"-Dspring.profiles.active=release", "-jar", "/app.jar"]