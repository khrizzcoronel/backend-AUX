# Fase 1: Build
FROM eclipse-temurin:25-jdk-alpine as build
WORKDIR /app
COPY . .

# Esto limpia cualquier caracter extraño de Windows en el script de Maven
RUN tr -d '\r' < mvnw > mvnw_unix && mv mvnw_unix mvnw
RUN chmod +x mvnw

# Ejecutamos la compilación
RUN ./mvnw clean package -DskipTests

# Fase 2: Run
FROM eclipse-temurin:25-jre-alpine
WORKDIR /app

# Usamos un comodín para copiar el jar sin importar el nombre exacto
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

# Limitamos la memoria para que Render no mate el proceso (muy importante en plan Free)
ENTRYPOINT ["java", "-Xmx300m", "-Xms300m", "-jar", "app.jar"]