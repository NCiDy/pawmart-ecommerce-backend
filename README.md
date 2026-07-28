# 🐾 PawMart Backend

RESTful API for an e-commerce platform specializing in pet products, built with **Java Spring Boot** following **Layered Architecture**.

This project was developed to practice backend development, authentication, product management, shopping cart, order processing, Docker, and PostgreSQL.

---

## 🚀 Tech Stack

* Java 21
* Spring Boot 3.5.6
* Spring Security
* JWT Authentication
* Spring Data JPA (Hibernate)
* PostgreSQL
* Docker & Docker Compose
* Cloudinary
* Swagger (OpenAPI)
* Maven
* Lombok

---

## ✨ Features

### 🔐 Authentication

* User Registration
* User Login
* JWT Authentication
* Role-based Authorization (ADMIN / USER)

### 📦 Product Management

* Category CRUD
* Product CRUD
* Product Search & Pagination
* Upload Product Images
* Upload Multiple Images
* Thumbnail Management
* Delete Product Images

### 🛒 Shopping

* Shopping Cart
* Address Management
* Create Orders from Cart
* Automatic Order Item Creation
* Automatic Stock Update
* Order Cancellation with Stock Recovery

### 📊 Admin

* Dashboard Statistics
* Order Management
* Update Order Status
* Update Payment Status

---

## 🏗️ Architecture

The project follows the **Layered Architecture** pattern:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
PostgreSQL
```

---

## 📊 Dashboard

The Admin Dashboard provides:

* Total Users
* Total Categories
* Total Products
* Total Orders
* Pending Orders
* Completed Orders
* Cancelled Orders
* Total Revenue

---

## 📖 API Documentation

Swagger UI:

```text
http://localhost:8080/swagger-ui/index.html
```

---

## 🐳 Run the Project

### 1. Start PostgreSQL

```bash
docker compose up -d
```

### 2. Run Spring Boot

```bash
mvn spring-boot:run
```

---

## 📌 Project Highlights

* Layered Architecture
* RESTful API Design
* JWT Authentication & Authorization
* Bean Validation
* Global Exception Handling
* Dockerized PostgreSQL
* Cloudinary Image Management
* Swagger API Documentation

---

## 📂 Main Modules

* Authentication
* Category
* Product
* Product Image
* Cart
* Address
* Order
* Dashboard

---

## 👨‍💻 Author

**Nguyễn Công Dũng**
Java Backend Developer

GitHub: https://github.com/NCiDy
Email: congdung1409@gmail.com
