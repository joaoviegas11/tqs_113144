FROM eclipse-temurin:21-jdk

# Instalações básicas
RUN apt-get update && apt-get install -y \
    maven \
    git \
    curl \
    && rm -rf /var/lib/apt/lists/*

# Define diretório da app
WORKDIR /app

# Copia o wrapper
COPY .mvn .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw

# Prepara dependências para build
RUN ./mvnw dependency:go-offline

# Copia o restante código
COPY . .

# Porta da aplicação
EXPOSE 8080

# Comando default (redefinido no docker-compose também)
CMD ["./mvnw", "spring-boot:run"]
