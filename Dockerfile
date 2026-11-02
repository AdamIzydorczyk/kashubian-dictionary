# openjdk:17-jdk-alpine used to be the base here: the official openjdk images were
# retired from Docker Hub, so that tag no longer exists (docker.io/library/openjdk:
# 17-jdk-alpine: not found) and the image could not be built at all. Eclipse Temurin
# is the same Java 17 on the same Alpine base.
FROM eclipse-temurin:17-jdk-alpine
LABEL maintainer="Adam Izydorczyk"
COPY build/libs/kashubian-dic.jar kashubian-dic.jar
ENTRYPOINT ["java", "-jar", "-Xms1024m", "-Xmx4096m", "-XX:+UseG1GC", "/kashubian-dic.jar"]
EXPOSE 8080
