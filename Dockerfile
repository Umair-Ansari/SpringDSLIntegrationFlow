FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/i2-sftp-service-1.0-0.jar app.jar
ENV JAVA_OPTS=""
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar
