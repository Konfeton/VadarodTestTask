FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

COPY mvnw .
ADD .mvn ./.mvn

COPY pom.xml .
COPY src src

RUN --mount=type=cache,target=/root/.m2 ./mvnw -f pom.xml clean package -DskipTests


FROM eclipse-temurin:21-jre-alpine
WORKDIR /
COPY --from=build /app/target/*.jar vadarod-test-task.jar
ENTRYPOINT ["java", "-jar", "vadarod-test-task.jar"]