# Usa a imagem oficial do Maven 3.8.5 como base
FROM maven:3.8.5-openjdk-17 AS build

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia o arquivo pom.xml para o diretório de trabalho
COPY pom.xml .

# Baixa as dependências do Maven
RUN mvn dependency:go-offline

# Copia os arquivos do código-fonte para o diretório de trabalho
COPY src ./src

# Compila o código-fonte
RUN mvn package

# Usa a imagem oficial do OpenJDK 17 como base
FROM adoptopenjdk/openjdk17:alpine-slim

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia o arquivo JAR construído na fase anterior
COPY --from=build /app/target/*.jar app.jar

# Define o comando padrão para executar o aplicativo quando o contêiner for iniciado
CMD ["java", "-jar", "app.jar"]
