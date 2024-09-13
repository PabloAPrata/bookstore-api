# Etapa de construção
FROM openjdk:21-jdk-slim AS builder
WORKDIR /application

# Instalar dos2unix
RUN apt-get update && apt-get install -y dos2unix

# Copiar arquivos necessários para o build
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

# Converter o script mvnw para o formato UNIX
RUN dos2unix mvnw

# Compilar o projeto usando Maven Wrapper
RUN ./mvnw package -DskipTests

# Definir o arquivo JAR gerado
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Extrair camadas do Spring Boot para otimização
RUN java -Djarmode=layertools -jar app.jar extract

# Etapa final
FROM openjdk:21-jdk-slim
LABEL authors="pabloprata"
WORKDIR /application

# Copiar as camadas extraídas do builder
COPY --from=builder /application/dependencies/ ./dependencies/
COPY --from=builder /application/spring-boot-loader/ ./spring-boot-loader/
COPY --from=builder /application/snapshot-dependencies/ ./snapshot-dependencies/
COPY --from=builder /application/application/ ./application/

# Definir o entrypoint para iniciar a aplicação
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
EXPOSE 8080
