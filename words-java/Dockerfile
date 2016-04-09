FROM java:openjdk-8u72-jdk

ENV MAVEN_VERSION 3.3.9
ENV MAVEN_HOME /usr/share/maven

RUN mkdir -p /usr/share/maven \
  && curl -fsSL http://apache.osuosl.org/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz \
    | tar -xzC /usr/share/maven --strip-components=1 \
  && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

WORKDIR /home/lab
ENTRYPOINT ["java", "-DPROD_MODE=true", "-Xmx128m", "-jar", "target/words.jar"]
EXPOSE 8888

ADD pom.xml .
RUN mvn verify -DskipTests --fail-never

ADD . ./
RUN mvn verify -DskipTests
