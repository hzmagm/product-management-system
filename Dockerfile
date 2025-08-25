# Étape 1 : Build avec Maven
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Étape 2 : Image légère pour run
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/product-management-app-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
