# ecommerce-challenge

Enterprise e-commerce solution featuring product CRUD, search, CSV import, purchase workflow, purchase history, and Dockerized local setup.

# Ecommerce Challenge

Enterprise-grade e-commerce application built with Java 21 and Spring Boot.

---

# Overview

This project implements an enterprise-style e-commerce platform that supports:

* Product management
* Product search
* CSV product import
* Purchase processing
* Stock validation
* Purchase history
* Web UI using Thymeleaf
* Dockerized local execution

The application was designed following layered architecture principles and focusing on maintainability, readability, and containerized deployment.

---

# Tech Stack

## Backend

* Java 21
* Spring Boot
* Spring Web
* Spring Data JPA
* Bean Validation
* MapStruct
* Lombok

## Frontend

* Thymeleaf
* HTML5
* CSS3
* Bootstrap 5

## Database

* PostgreSQL

## Database Migration

* Flyway

## Infrastructure

* Docker
* Docker Compose

## Testing

* JUnit 5
* Spring Boot Test

---

# Features

## Product Management

* Create products
* Update products
* Delete products
* Get product by id
* List products

---

## Product Search

Search products by:

* Name
* Category
* SKU

---

## CSV Import

* Import products from CSV file
* Validation during import
* Duplicate SKU prevention
* Invalid data handling
* CSV sanitization before persistence

Examples of handled validations:

* Invalid price
* Empty required fields
* Negative stock
* Duplicate SKU

---

## Purchase Flow

* Purchase validation
* Stock availability verification
* Fake payment simulation
* Stock update after purchase
* Purchase history tracking

---

## Web UI

The application includes a server-side rendered UI built with Thymeleaf and Bootstrap.

Available screens:

* Product list
* Product search
* Create product
* Edit product
* Delete product
* CSV import
* Purchase products
* Purchase history

---

# Architecture

Layered Architecture:

```plaintext
Controller
↓
Service
↓
Repository
↓
Database
```

Project structure:

```plaintext
src
├── main
│   ├── java
│   │   └── com.ray.ecommerce
│   │       ├── product
│   │           ├── controller
│   │           ├── service
│   │           ├── repository
│   │           ├── entity
│   │           ├── mapper
│   │           ├── dto
│   │           └── exceptions
│   │           
│   │
│   └── resources
│       ├── templates
│       ├── db
│       │   └── migration
│       └── application.properties
```

---

# Design Decisions

## Spring Boot

Chosen for rapid development and production-ready features.

---

## PostgreSQL

Selected for reliability, relational consistency, and SQL support.

---

## Flyway

Used for version-controlled schema evolution.

---

## Docker

Used to simplify local setup and ensure environment consistency.

---

## Thymeleaf

Selected to deliver a complete UI quickly while keeping deployment simple.

---

## MapStruct

Used to reduce manual mapping boilerplate.

---

# API Endpoints

## Products

| Method | Endpoint             |
| ------ | -------------------- |
| POST   | /api/products        |
| GET    | /api/products        |
| GET    | /api/products/{id}   |
| PUT    | /api/products/{id}   |
| DELETE | /api/products/{id}   |
| GET    | /api/products/search |

---

## CSV Import

| Method | Endpoint             |
| ------ | -------------------- |
| POST   | /api/products/import |

---

## Purchase

| Method | Endpoint            |
| ------ | ------------------- |
| POST   | /api/purchases      |
| GET    | /api/purchases      |
| GET    | /api/purchases/{id} |

---

# UI Navigation

## Products Screen

Open:

```plaintext
http://localhost:8080/products
```

Available actions:

* Search products
* Create product
* Edit product
* Delete product
* Import CSV
* Purchase product
* Navigate to purchase history

---

## Create Product

```plaintext
http://localhost:8080/products/new
```

---

## Purchase History

```plaintext
http://localhost:8080/purchases/history
```

Displays:

* Product purchased
* Quantity
* Price
* Total
* Purchase date
* Status

---

# Database Initialization

Database schema is managed automatically through Flyway.

Migrations:

```plaintext
src/main/resources/db/migration

create_tables.sql

```

---

# Running Locally

## Clone repository

```bash
git clone https://github.com/[YOUR_USERNAME]/ecommerce-challenge.git
```

---

## Start infrastructure

```bash
docker compose up -d
```

---

## Run application

```bash
mvn clean install
mvn spring-boot:run
```

---

## Access Application

### UI

```plaintext
http://localhost:8080/products
```

### Products API

```plaintext
http://localhost:8080/api/products
```

### Purchase API

```plaintext
http://localhost:8080/api/purchases
```

### Purchase History

```plaintext
http://localhost:8080/purchases/history
```

---

# CSV Example

Example CSV structure:

```csv
name,sku,description,category,price,stock,weight_kg
Laptop,SKU001,Gaming Laptop,Electronics,15000,10,2.10
Mouse,SKU002,Wireless Mouse,Accessories,400,50,0.20
```

---

# Alternatives Considered

* MySQL instead of PostgreSQL
* Liquibase instead of Flyway
* React instead of Thymeleaf
* Manual mapping instead of MapStruct

PostgreSQL and Flyway were selected for simplicity and maintainability.

---

# Challenge Notes

CSV download date:

```plaintext
2026-06-15
```

Development period:

```plaintext
2026-06-22 → 2026-06-26
```

---

# Future Improvements

* Authentication and authorization
* Swagger / OpenAPI
* Pagination
* Sorting
* Advanced filtering
* Event-driven purchase processing
* Integration tests
* Testcontainers
* CI/CD pipeline
* Cloud deployment

---

# Author

Raymundo Ricardo Ortiz Luviano
