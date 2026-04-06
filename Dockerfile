# Stage 1: Build application
FROM eclipse-temurin:21-jdk-jammy AS builder

WORKDIR /app

# Install Maven
RUN apt-get update && apt-get install -y maven

# Copy pom.xml first
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline

# Copy source code
COPY src ./src

# Build jar
RUN mvn clean package -DskipTests

# Stage 2: Run application
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Copy built jar
COPY --from=builder /app/target/*.jar app.jar

# IMPORTANT
EXPOSE 8080

# Start application
ENTRYPOINT ["java","-jar","app.jar"]
