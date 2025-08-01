FROM amazoncorretto:21-alpine-jdk

# Crea un directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo .jar desde target/
COPY target/app-0.0.1-SNAPSHOT.jar app.jar

# Ejecuta la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
