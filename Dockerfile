FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} troknite.jar
ENTRYPOINT ["java","-jar","/troknite.jar"]