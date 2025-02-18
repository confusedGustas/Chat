# 🚀 WebSocket Chat Application

A real-time chat application using WebSockets for instant messaging and Spring Boot for the backend.

## 🔗 Services & Access Information

| 🛠 Service        | 🌍 URL / Port                                  | 👤 Username       | 🔑 Password  |
|-------------------|------------------------------------------------|-------------------|--------------|
| **Spring Boot**   | `http://localhost:8080`                        | -                 | -            |
| **PostgreSQL**    | `localhost:5432`                               | `admin`           | `admin`      |
| **pgAdmin**       | [http://localhost:8282](http://localhost:8282) | `admin@gmail.com` | `admin`      |
| **Frontend**      | [http://localhost:5173](http://localhost:5173) | -                 | -            |

## 📝 Environment Variables

The environment variables are configured in the `.env` file that you need to create in the root directory:

```sh
# PostgreSQL
POSTGRES_USERNAME: postgres
POSTGRES_PASSWORD: postgres

# Spring Boot
SPRING_USERNAME: admin
SPRING_PASSWORD: admin

# pgAdmin
PGADMIN_USERNAME: admin@gmail.com
PGADMIN_PASSWORD: admin
```

# Project Setup

## Prerequisites

- **Java 23**
- **Maven**
- **Docker**
- **Node.js**

## Running the Services

### 🚢 Start Service

```sh
docker compose up
```

### 🚀 Start Spring Boot

```sh
./mvnw spring-boot:run
```

## 📜 License

This project is licensed under the **MIT License**. See the [LICENSE](LICENSE) file for details.
