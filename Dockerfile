# Используем официальный образ с поддержкой JDK 23
FROM openjdk:17-jdk-slim

# Устанавливаем Maven
RUN apt-get update && apt-get install -y maven

# Устанавливаем рабочую директорию в контейнере
WORKDIR /app

# Копируем pom.xml и весь проект в контейнер
COPY pom.xml ./
COPY src ./src

# Собираем проект с помощью Maven
RUN mvn clean package -DskipTests


# Открываем порт 8080
EXPOSE 8082

# Указываем команду для запуска приложения
CMD ["java", "-jar", "target/Backend_For_Gps_Tracker_Android-0.0.1-SNAPSHOT.jar"]
