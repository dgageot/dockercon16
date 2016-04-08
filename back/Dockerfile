FROM maven:3.3.9-jdk-8

WORKDIR /home/lab
ENTRYPOINT ["java", "-DPROD_MODE=true", "-Xmx2G", "-jar", "target/backend.jar"]
EXPOSE 8080

ADD . ./
RUN mvn verify -DskipTests
