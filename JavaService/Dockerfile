# --- build: compila el jar con Maven ---
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Primero solo el pom para cachear la descarga de dependencias:
# si el pom no cambia, Docker reutiliza esta capa en builds sucesivos.
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Ahora el codigo y el empaquetado (sin tests para acelerar la imagen).
COPY src ./src
RUN mvn clean package -DskipTests -B

# --- runtime: imagen final minima solo con el JRE ---
FROM eclipse-temurin:21-jre-alpine AS runtime
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
