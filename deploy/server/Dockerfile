# syntax=docker/dockerfile:1
FROM maven:3.9-eclipse-temurin-8 AS build
WORKDIR /build
COPY deploy/server/maven-settings.xml /tmp/maven-settings.xml
COPY cate-server/pom.xml ./pom.xml
COPY cate-server/src ./src
RUN --mount=type=cache,target=/root/.m2 \
    mvn -B -s /tmp/maven-settings.xml -DskipTests package

FROM eclipse-temurin:8-jre-jammy
WORKDIR /app
RUN groupadd --system cate \
    && useradd --system --uid 10001 --gid cate cate \
    && mkdir -p img avatorImages food mv \
    && chown -R cate:cate /app
COPY --from=build --chown=cate:cate /build/target/cate-0.0.1-SNAPSHOT.jar ./cate.jar
COPY --chown=cate:cate cate-server/img ./img
USER cate
EXPOSE 8888
ENTRYPOINT ["java", "-jar", "/app/cate.jar"]
