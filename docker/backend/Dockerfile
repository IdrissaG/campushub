# Etape 1 : build
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY campushub/pom.xml .
COPY campushub/src ./src
RUN mvn clean package -DskipTests

# Etape 2 : image finale
FROM eclipse-temurin:17-jre-alpine
RUN apk add --no-cache curl
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
