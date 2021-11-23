FROM openjdk:8-jdk-alpine
ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
ADD https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v1.7.2/opentelemetry-javaagent-all.jar /app/lib/opentelemetry-javaagent-all.jar
ENTRYPOINT ["java","-cp","app:app/lib/*","hello.Application","${JAVA_OPTS}"]