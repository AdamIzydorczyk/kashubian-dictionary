FROM openjdk:17-jdk-alpine
MAINTAINER Adam Izydorczyk
COPY build/libs/kashubian-dic.jar kashubian-dic.jar
ENTRYPOINT ["java", "-jar", "-Xms1024m", "-Xmx4096m", "-XX:+UseG1GC", "/kashubian-dic.jar"]
EXPOSE 8080
