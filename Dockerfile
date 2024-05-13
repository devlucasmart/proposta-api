# Usa a imagem oficial do Maven 3.8.5 como base
FROM maven:3.8.5-openjdk-17

COPY target/proposta-api-0.0.1-SNAPSHOT.jar app.jar

COPY wait-for-it.sh wait-for-it.sh

RUN chmod +x wait-for-it.sh

EXPOSE 8080

ENTRYPOINT ["./wait-for-it.sh", "rabbit:5672", "--", "java", "-Duser.language=pt", "-Duser.country=BR", "-jar", "app.jar"]