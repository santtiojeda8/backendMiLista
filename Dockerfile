# Usamos una imagen oficial de Java (OpenJDK) ligera como base
FROM eclipse-temurin:21-jdk-alpine

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiamos el archivo JAR que genera Maven al contenedor
# (Asegúrate de haber corrido 'mvn clean package' al menos una vez antes)
COPY target/*.jar app.jar

# Exponemos el puerto en el que corre tu API de Spring Boot
EXPOSE 8080

# Comando para ejecutar la aplicación cuando encienda el contenedor
ENTRYPOINT ["java", "-jar", "app.jar"]