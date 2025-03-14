# Javers Auditing

**Application Applying Database Auditing Using Spring Data JDBC with Javers and Postgres**

This project demonstrates how to integrate [Javers](https://javers.org/) with Spring Data JDBC to provide auditing capabilities for your database operations. The application is built with Spring Boot and supports auditing of changes on your entities via REST endpoints. Although Postgres is intended for production use, an in-memory H2 database is also supported for development and testing purposes.

## Table of Contents

- [Features](#features)
- [Technology Stack](#technology-stack)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Clone the Repository](#clone-the-repository)
  - [Running the Application](#running-the-application)
- [Configuration](#configuration)
- [API Endpoints](#api-endpoints)
- [Auditing with Javers](#auditing-with-javers)
- [Project Structure](#project-structure)

## Features

- **Auditing:**  
  Records changes to domain entities using Javers. View audit snapshots and history via dedicated endpoints.
- **Spring Data JDBC Integration:**  
  Uses Spring Data JDBC for repository access while leveraging Javers for auditing without interfering with your domain tables.
- **REST API:**  
  Exposes endpoints for CRUD operations and audit history retrieval.
- **Swagger/OpenAPI Integration:**  
  API documentation is generated automatically with Springdoc OpenAPI.
- **Dockerized Database:**  
  Option to run PostgreSQL (or H2 for testing) using Docker Compose.

## Technology Stack

- **Spring Boot 3.4.3**
- **Spring Data JDBC**
- **Javers 7.8.0**
- **PostgreSQL / H2 Database**
- **MapStruct** for DTO mapping
- **Lombok** for reducing boilerplate code
- **Springdoc OpenAPI** for API documentation

## Getting Started

### Prerequisites

- JDK 17 or higher
- Maven
- Docker (for running PostgreSQL if desired)

### Clone the Repository

```bash
git clone https://github.com/youssefGamalMohamed/spring-database-autiding-javers.git
cd spring-database-autiding-javers
```

### Running the Application

#### Using Maven

You can build and run the application using Maven:

```bash
./mvnw clean install
./mvnw spring-boot:run
```

#### Using Docker Compose (PostgreSQL)

If you want to use PostgreSQL instead of H2, ensure you have Docker installed and then run:

```bash
docker-compose up
```

This command uses the provided Docker Compose configuration to start a PostgreSQL container.

## Configuration

The application uses configuration files to manage data sources and Javers settings. For example, in `src/main/resources/application.yml` you might have:

```yaml
spring:
  application:
    name: javers-auditing
  datasource:
    url: jdbc:postgresql://localhost:5432/mydatabase
    username: myuser
    password: secret
    driver-class-name: org.postgresql.Driver
  jpa:
    database-platform: org.hibernate.dialect.PostgreSQLDialect

javers:
  sql:
    dialect: PostgreSQL
    schema: PUBLIC
  mappingStyle: FIELD
  algorithm: SIMPLE
  commitIdGenerator: synchronized_sequence
  prettyPrint: true
  typeSafeValues: false
  initialChanges: true
  terminalChanges: true
  auditableAspectEnabled: true
  springDataAuditableRepositoryAspectEnabled: true
  usePrimitiveDefaults: true
  prettyPrintDateFormats:
    localDateTime: 'dd MMM yyyy, HH:mm:ss'
    zonedDateTime: 'dd MMM yyyy, HH:mm:ssZ'
    localDate: 'dd MMM yyyy'
    localTime: 'HH:mm:ss'
```

For development with H2, you can use a different configuration (see [application.properties](#)).

## API Endpoints

### CRUD Endpoints for Post

- **POST** `/posts` – Create a new post  
- **GET** `/posts/{id}` – Get post by ID  
- **PUT** `/posts/{id}` – Update a post  
- **DELETE** `/posts/{id}` – Delete a post  
- **GET** `/posts/` – List or search posts

### Auditing Endpoint

- **GET** `/posts/{id}/history` – Get the audit history for a post

> Note: The auditing endpoint returns snapshots of the changes made to a post entity, using Javers.

## Auditing with Javers

This project integrates Javers to track changes on your `Post` entity. Changes are committed to Javers in the service layer using:
```java
javers.commit("system", post);
```
Audit snapshots are stored in dedicated tables (e.g. `jv_global_id`, `jv_snapshot`, etc.). The endpoint `/posts/{id}/history` queries these snapshots and returns the audit history in JSON format.

## Project Structure

The project follows a standard Maven structure:

```
spring-database-autiding-javers/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── youssef/
│   │   │           └── gamal/
│   │   │               └── javers_auditing/
│   │   │                   ├── configs/         # Security, JPA configuration, etc.
│   │   │                   ├── controllers/     # REST controllers
│   │   │                   ├── dtos/            # Data Transfer Objects (e.g., PostDto)
│   │   │                   ├── entities/        # Domain entities (e.g., Post)
│   │   │                   ├── mappers/         # MapStruct mappers (e.g., PostMapper)
│   │   │                   ├── repos/           # Repository interfaces
│   │   │                   └── services/        # Service layer logic
│   │   └── resources/
│   │       ├── application.yml                # Application configuration
│   │       └── data.sql                       # SQL data initialization (if used)
│   └── test/
│       └── java/                              # Test code
├── pom.xml
└── README.md                                  # This file
```

