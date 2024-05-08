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

# Agora não há necessidade de definir a segunda fase usando o OpenJDK 17, pois já fizemos tudo o que precisamos na primeira fase.

# Define o comando padrão para executar o aplicativo quando o contêiner for iniciado
CMD ["java", "-jar", "target/*.jar"]
