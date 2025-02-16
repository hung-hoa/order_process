# 🚀 **Order Processing System**

A simple **Order Processing System** built with **Spring Boot**, using **MongoDB, Redis, Kafka**, and **Docker**.

## 📌 **Features**
👉 **User Authentication** (Register & Login)  
👉 **Order Management** (Create, Retrieve, List, Delete Orders)  
👉 **MongoDB** for order storage  
👉 **Redis** for caching  
👉 **Kafka** for event-driven processing  
👉 **Docker Compose** for containerized deployment

## 🏢 **Technologies Used**
- **Spring Boot**
- **Spring Security** (JWT-based authentication)
- **MongoDB** (NoSQL database)
- **Redis** (Caching layer)
- **Kafka** (Message broker for event-driven architecture)
- **Docker & Docker Compose** (Containerization)

## 🚀 **Getting Started**

### 1⃣ **Clone the Repository**
```sh
git clone ...
cd order-process  
```

### 2⃣ **Run the Project with Docker**
Make sure Docker is installed, then run:
```sh
docker compose -f docker/docker-compose-backend.yml up --build -d  
```

### 3⃣ **API Endpoints**

#### 🔑 **Authentication**
- **Register** → `POST /api/auth/register`
- **Login** → `POST /api/auth/login`

#### 📦 **Order Management**
- **Create Order** → `POST /api/order`
- **Get Order by ID** → `GET /api/order/{id}`
- **Get All Orders** → `GET /api/order?page=0&size=10`
- **Delete Order** → `DELETE /api/order/{id}`

### 4⃣ **Stop the Application**
To stop all services, run:
```sh
docker compose -f docker/docker-compose-backend.yml down  
```
### Hoa Thanh Hung
