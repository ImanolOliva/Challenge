# Usa una imagen base con OpenJDK 17
FROM openjdk:17-jdk-slim AS build

# Instala Maven
RUN apt-get update && apt-get install -y maven

# Copia el código fuente al contenedor
COPY . /app

# Establece el directorio de trabajo en el contenedor
WORKDIR /app

# Ejecuta Maven para construir la aplicación
RUN mvn clean install

# Usamos una imagen base más ligera para ejecutar la aplicación
FROM openjdk:17-jdk-slim

# Copia el archivo .jar generado desde la etapa de construcción
COPY --from=build /app/target/core-0.0.1-SNAPSHOT.jar app.jar

# Configura el contenedor para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app.jar"]
