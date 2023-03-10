FROM openjdk:12
LABEL maintainer="Wanderson Reliquias"
ADD /target/democrud01-0.0.1-SNAPSHOT.jar democrud01.jar
ENTRYPOINT ["java", "-jar", "democrud01.jar"]