FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . /app
RUN mvn clean package

FROM openjdk:17-jdk-alpine AS runtime
WORKDIR /app
COPY --from=build /app/target/ProyectoDEF-0.0.1-SNAPSHOT.jar /app/ProyectoDEF-0.0.1-SNAPSHOT.jar
COPY --from=build /app/Errores /app/Errores
COPY --from=build /app/uploads /app/uploads
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/ProyectoDEF-0.0.1-SNAPSHOT.jar"]
