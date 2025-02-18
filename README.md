# 🚀 WebSocket Chat Application

A real-time chat application using WebSockets for instant messaging and Spring Boot for the backend.

---

## 🔗 Services & Access Information

| 🛠 Service        | 🌍 URL / Port                                  | 👤 Username       | 🔑 Password  |
|-------------------|------------------------------------------------|-------------------|--------------|
| **Spring Boot**   | `http://localhost:8080`                        | -                 | -            |
| **PostgreSQL**    | `localhost:5432`                               | `admin`           | `admin`      |
| **pgAdmin**       | [http://localhost:8282](http://localhost:8282) | `admin@gmail.com` | `admin`      |
| **Frontend**      | [http://localhost:5173](http://localhost:5173) | -                 | -            |

---

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

### 🚢 Start All Services

```sh
docker compose up
```

### 🚀 Start Spring Boot

```sh
./mvnw spring-boot:run
```

### 🛠 Optional

### 1️⃣ Open pgAdmin

- Navigate to [http://localhost:8282](http://localhost:8282)
- Log in with:
    - **Username**: `admin@gmail.com`
    - **Password**: `admin`

### 2️⃣ Register a New Server

- Right-click **Servers** in the Browser panel → **Register** → **Server...**

### 3️⃣ Configure the First Server

- **General Tab**:
    - Name: `chat`
- **Connection Tab**:
    - Host: `host.docker.internal`
    - Port: `5432`
    - Username: `postgres`
    - Password: `postgres`