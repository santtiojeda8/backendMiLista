# Usamos una imagen oficial de Java (OpenJDK) ligera como base

# ETAPA 1: Compilacion
# Usamos la imagen de Java 21 basada en Alpine Linux. La etiqueta AS build le da un nombre para referenciarla después.
FROM eclipse-temurin:21-jdk-alpine AS build
# Aquí establecemos /workspace como directorio de trabajo dentro del contenedor. Lo que se ejecute después, como COPY/RUN ocurre ahí.
WORKDIR /workspace
# Copia la carpeta .mvn (que contiene la configuración del Maven Wrapper) al contenedor. Necesaria para que ./mvnw funcione.
COPY .mvn .mvn
#
COPY mvnw pom.xml ./
# Aquí damos permisos de ejecución al wrapper y descarga todas las dependencias de Maven sin compilar todavía. Esto aprovecha la caché de Docker: si no cambias el pom.xml, no vuelve a descargar dependencias en builds posteriores.
RUN chmod +x mvnw && ./mvnw -B -ntp dependency:go-offline
#Copiamos el código fuente del contenedor. Se hace después de descargar dependencias para que un cambio en el código no invalidé la cache.
COPY src src
# Esta línea compila el proyecto y genera el .jar en /target, saltando los tests.
RUN ./mvnw -B -ntp -DskipTests package



# ETAPA 2: Ejecución
# Indicamos una imagen base más ligera, solo con el JRE (no el JDK completo). El contenedor final será más pequeño.
FROM eclipse-temurin:21-jre-alpine
# Indicamos el directorio de trabajo para la etapa de ejecución
WORKDIR /app
#Copia el .jar generado en la etapa build al contenedor final, renombrandolo a app.jar
COPY --from=build /workspace/target/*.jar app.jar



#Ajuste de memoria para el contenedor de 512mb
ENV JAVA_TOOL_OPTIONS="-XX:MaxRAMPercentage=70 -XX:+ExitOnOutOfMemoryError"



# Exponemos el puerto en el que corre tu API de Spring Boot. Render lo usará para enrutar el tráfico
EXPOSE 8080



# Comando para ejecutar la aplicación cuando encienda el contenedor
ENTRYPOINT ["java", "-jar", "app.jar"]

#Comando para armar el archivo .jar para docker y ejecutar el docker.compose.yaml para armar el contenedor
#   mvn clean packahe -DskipTests
#   docker compose up -d --build