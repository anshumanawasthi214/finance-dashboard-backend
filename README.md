# Finance Dashboard Backend System

## Project Overview

The Finance Dashboard Backend System is a role-based backend application designed to manage financial records securely. It supports multiple user roles, transaction management, and dashboard-level financial summaries.

This project demonstrates backend development concepts such as:

* REST API design
* Role-Based Access Control (RBAC)
* JWT Authentication
* Data persistence using JPA
* Input validation and error handling

The system uses **H2 database by default**, with optional support for **MySQL**.

---

# Technologies Used

* Java 17
* Spring Boot
* Spring Security
* JWT Authentication
* Spring Data JPA
* H2 Database (Default)
* MySQL (Optional Support)
* Maven
* Swagger (OpenAPI Documentation)
* Lombok

---

# Core Features

## Authentication System

* JWT-based authentication
* Secure login system
* Token-based authorization
* Protected API endpoints

Users must authenticate before accessing secured APIs.

---

## Role-Based Access Control

The system supports three roles:

### ADMIN

Can:

* Create users
* View users
* Update user status
* Delete users
* Create transactions
* Update transactions
* Delete transactions
* View dashboard summaries

---

### ANALYST

Can:

* Create transactions
* Update transactions
* View transactions
* View dashboard summaries

Cannot:

* Manage users
* Delete users

---

### VIEWER

Can:

* View transactions
* View dashboard summaries

Cannot:

* Create transactions
* Update transactions
* Delete transactions

---

# Transaction Management

## Transaction Fields

Each transaction includes:

* Amount
* Type (INCOME / EXPENSE)
* Category
* Date
* Note / Description

---

## Supported Operations

* Create Transaction
* Get All Transactions
* Get Transaction By ID
* Update Transaction
* Delete Transaction


---

# Filtering Support

Transactions can be filtered using:

* Type
* Category
* Date Range
* Pagination 

Example:

GET /transactions?type=EXPENSE

GET /transactions?category=Food

GET /transactions?startDate=2026-01-01&endDate=2026-12-31

---

# Dashboard Summary APIs

The system provides aggregated financial insights:

* Total Income
* Total Expense
* Net Balance
* Category-wise totals
* Monthly summary

These APIs support financial dashboard analytics.

---

# Project Architecture

The project follows layered architecture:

controller — Handles API endpoints

service — Contains business logic

repository — Handles database operations

model — Entity classes

dto — Request and response objects

security — Authentication and authorization

config — Application configuration

This separation improves maintainability and scalability.

---

# Database Configuration

## Default Database — H2

The project uses **H2 in-memory database** by default.

Configuration:

spring.datasource.url=jdbc:h2:mem:finance-db

spring.datasource.driverClassName=org.h2.Driver

spring.datasource.username=sa

spring.datasource.password=

spring.jpa.hibernate.ddl-auto=update

spring.h2.console.enabled=true

spring.h2.console.path=/h2-console

---

## Access H2 Console

URL:

http://localhost:8080/h2-console

Use:

JDBC URL:
jdbc:h2:mem:finance-db

Username:
sa

Password:
(blank)

---

# Setup Instructions

Follow these steps to run the project locally.

---

## Prerequisites

Make sure the following are installed:

* Java 17 or later
* Maven
* Spring Tool Suite (STS) or IntelliJ IDEA
* Git

Optional:

* MySQL (only if switching from H2)

---

## Step 1 — Clone Repository

git clone <repository-url>

Or download ZIP and extract.

---

## Step 2 — Open Project in IDE

Open using:

Spring Tool Suite (STS)

OR

IntelliJ IDEA

Wait for Maven dependencies to download.

---

## Step 3 — Verify application.properties

Ensure H2 configuration exists:

spring.datasource.url=jdbc:h2:mem:finance-db

spring.datasource.driverClassName=org.h2.Driver

spring.datasource.username=sa

spring.datasource.password=

spring.jpa.hibernate.ddl-auto=update

spring.h2.console.enabled=true

spring.h2.console.path=/h2-console

---

## Step 4 — Run Application

Run as:

Spring Boot Application

OR:

mvn spring-boot:run

Application runs at:

http://localhost:8080

---

## Step 5 — Access Swagger

Swagger UI:

http://localhost:8080/swagger-ui/index.html

Swagger allows:

* Testing APIs
* Viewing responses
* Understanding endpoints

---

## Step 6 — Login User

POST /auth/login

Example Request:

{
"email": "[admin@example.com](mailto:admin@example.com)",
"password": "admin123"
}

Response:

JWT Token returned.

---

## Step 7 — Add Authorization Header

Authorization: Bearer <JWT_TOKEN>

Now secured APIs can be accessed.

---

# How to Switch Database from H2 to MySQL

Optional migration to MySQL.

---

## Step 1 — Install MySQL

Install:

MySQL Community Server

(Optional)

MySQL Workbench

---

## Step 2 — Add MySQL Dependency

Add in pom.xml:

<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <scope>runtime</scope>
</dependency>

---

## Step 3 — Update application.properties

Replace H2 configuration with:

spring.datasource.url=jdbc:mysql://localhost:3306/finance_dashboard?createDatabaseIfNotExist=true

spring.datasource.username=root

spring.datasource.password=root123

spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect

spring.jpa.hibernate.ddl-auto=update

---

## Step 4 — Run Application

Spring Boot automatically:

* Creates database
* Creates tables
* Updates schema

No manual table creation required.

---

# API Testing Workflow

Recommended flow:

1. Login using /auth/login
2. Copy JWT token
3. Add Authorization header
4. Test APIs

Header Format:

Authorization: Bearer <JWT_TOKEN>

---

# Security Features

* JWT Authentication
* Role-Based Authorization
* Protected APIs
* Secure password encryption

---

# Error Handling

The system includes:

* Input validation
* HTTP status codes
* Exception handling
* Meaningful error messages

---

# Future Improvements

Possible enhancements:

* Soft delete support
* Unit testing
* Integration testing
* Docker containerization
* Logging system
* Rate limiting
* Audit logging

---

# Author

Finance Dashboard Backend System

Backend Development Project

Designed to demonstrate backend architecture and secure system development.

---

# License

This project is created for educational and evaluation purposes.
