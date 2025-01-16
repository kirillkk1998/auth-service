# Auth Service

REST-сервис для аутентификации и управления пользователями с использованием Keycloak.

## Требования

- Java 17
- Docker и Docker Compose
- Maven

## Запуск

1. Клонируйте репозиторий:

bash
git clone <repository-url>


2. Запустите сервисы через Docker Compose:

bash
docker-compose up -d


3. Настройте Keycloak (http://localhost:8080):
    - Войдите в админ-консоль (admin/admin)
    - Создайте realm "auth-service"
    - Создайте client "auth-client"
    - Настройте client credentials

4. Сервис будет доступен по адресу: http://localhost:8081

## API Документация

Swagger UI доступен по адресу: http://localhost:8081/swagger-ui.html

## Мониторинг

- Prometheus: http://localhost:9090
- Grafana: http://localhost:3000

## Тестирование

Запуск тестов:

bash
./mvnw test

