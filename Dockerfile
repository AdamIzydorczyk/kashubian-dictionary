FROM openjdk:17-jdk-alpine
MAINTAINER Adam Izydorczyk
COPY build/libs/kashubian-dic.jar kashubian-dic.jar
ENTRYPOINT ["java", "-jar", "-Xms512m", "-Xmx1024m", "-XX:+UseSerialGC", "/kashubian-dic.jar"]
EXPOSE 8080
