# JaVers Auditing Demo

This is a Spring Boot application demonstrating comprehensive entity auditing using both **JaVers** and **Spring Data JPA Auditing**. It provides a simple REST API for managing `Post` entities and allows retrieving their detailed change history.

## Key Features

*   **CRUD Operations:** Standard RESTful endpoints for Creating, Reading, Updating, and Deleting `Post` entities.
*   **JaVers Auditing:** Automatic, fine-grained auditing of changes to `Post` entities (creations, updates, deletions) managed by JaVers.
    *   Captures snapshots of entity state at each change.
    *   Tracks changes to specific fields (`title`, `content`).
*   **Spring Data JPA Auditing:** Standard auditing for creation/modification metadata (`createdBy`, `creationDate`, `lastModifiedBy`, `lastModifiedDate`) using Spring Data JPA's built-in features.
*   **Audit History API:** An endpoint (`GET /posts/{id}/histories`) to retrieve the complete version history of a specific `Post`, showing changes, responsible user, and state at each version.
*   **User Tracking:** Integrates with Spring Security to automatically capture the username of the authenticated user performing the changes for both JaVers commits and JPA audit fields.
*   **Custom Commit Metadata:** Demonstrates adding custom metadata (e.g., `tenant`) to JaVers commits using `CommitPropertiesProvider`.
*   **DTO Pattern:** Uses Data Transfer Objects (`PostDto`, `HistoryDto`) for clean API contracts.
*   **MapStruct Mapping:** Leverages MapStruct for efficient and type-safe mapping between Entities and DTOs.
*   **Spring Security Integration:** Basic security setup with in-memory users for authentication.
*   **Pagination:** Supports paginated retrieval of `Post` entities (`GET /posts/`).
*   **Clear Separation:** Demonstrates how JaVers and Spring Data JPA Auditing can coexist, with JPA auditing fields ignored by JaVers (`@DiffIgnore`).

## Technologies Used

*   Java (JDK 17+)
*   Spring Boot 3.x
*   Spring Data JPA
*   Spring Security
*   JaVers (Core, Spring Boot Starter Data JPA)
*   PostgreSQL (or any compatible SQL database)
*   MapStruct
*   Lombok
*   Maven / Gradle

## Setup and Running

1.  **Clone the Repository:**
    ```bash
    git clone <your-repository-url>
    cd <repository-directory>
    ```

2.  **Database Setup:**
    *   Ensure you have a PostgreSQL database running.
    *   Create a database named `mydatabase`.
    *   Create a user `myuser` with password `secret` and grant necessary permissions on the `mydatabase`.
    *   Alternatively, update the database connection details in `src/main/resources/application.properties`:
        ```properties
        spring.datasource.url=jdbc:postgresql://<your_db_host>:<your_db_port>/<your_db_name>
        spring.datasource.username=<your_db_user>
        spring.datasource.password=<your_db_password>
        ```
    *   JaVers requires its own tables to store audit data. These tables (`jv_global_id`, `jv_commit`, `jv_commit_property`, `jv_snapshot`) will be created or updated automatically on application startup if `spring.jpa.hibernate.ddl-auto` is set to `update` or `create`.

3.  **Build the Application:**
    *   Using Maven:
        ```bash
        mvn clean install
        ```
    *   Using Gradle:
        ```bash
        ./gradlew clean build
        ```

4.  **Run the Application:**
    *   Using Maven:
        ```bash
        mvn spring-boot:run
        ```
    *   Using Gradle:
        ```bash
        ./gradlew bootRun
        ```
    *   Or run the JAR file:
        ```bash
        java -jar target/javers-auditing-*.jar
        ```

The application will start on `http://localhost:8080` (or the configured port).

## API Endpoints

The primary endpoints are under `/posts`:

*   `POST /posts`: Create a new post. Requires authentication.
    *   Body: `PostDto` (`{"title": "...", "content": "..."}`)
*   `GET /posts/{id}`: Get a specific post by ID. Requires authentication.
*   `PUT /posts/{id}`: Update an existing post. Requires authentication.
    *   Body: `PostDto` (`{"title": "...", "content": "..."}`)
*   `DELETE /posts/{id}`: Delete a post by ID. Requires authentication.
*   `GET /posts/`: Get a paginated list of posts. Requires authentication.
    *   Supports standard Spring Data pagination parameters (e.g., `?page=0&size=10&sort=title,asc`).
*   `GET /posts/{id}/histories`: Get the audit history for a specific post. Requires authentication.
    *   Returns a list of `HistoryDto`.

**Authentication:**

The application uses basic authentication or form login configured via Spring Security. Default users (defined in `SecurityConfig`):

*   Username: `Youssef`, Password: `1234`
*   Username: `Admin`, Password: `Admin`

Use these credentials when prompted by your HTTP client (like `curl`, Postman, or your browser).

## Auditing Implementation Details

*   **JaVers:** The `PostRepo` is annotated with `@JaversSpringDataAuditable`, enabling JaVers to automatically intercept `save()` and `deleteById()` calls to create audit snapshots. The `JaversAuthorProvider` bean fetches the current username from Spring Security for the `author` field in JaVers commits. The `CommitPropertiesProvider` adds extra context (like `"tenant":"tenant-1"`) to each commit.
*   **Spring Data JPA:** The `Post` entity uses `@EntityListeners(AuditingEntityListener.class)` and fields annotated with `@CreatedBy`, `@CreatedDate`, `@LastModifiedBy`, `@LastModifiedDate`. The `SecurityAuditorAware` bean provides the username for the `@CreatedBy` and `@LastModifiedBy` fields. These fields are marked with `@DiffIgnore` so JaVers doesn't track their changes directly, avoiding redundancy.
*   **History Retrieval:** The `HistoryController` uses the `JaversHistoryServiceImpl` which queries the JaVers repository (`javers.findSnapshots`) to get the historical `CdoSnapshot` objects for a given entity instance and maps them to `HistoryDto`.

---

**List of Key Features:**

1.  **CRUD Operations:** Full Create, Read, Update, Delete for `Post` entities via REST API.
2.  **JaVers Auditing:** Automatic snapshotting of `Post` entity changes (Create, Update, Delete).
3.  **Spring Data JPA Auditing:** Tracks creation/modification user and timestamp (`createdBy`, `creationDate`, etc.).
4.  **Combined Auditing Strategy:** Demonstrates using both JaVers (for detailed field changes) and JPA Auditing (for metadata) effectively.
5.  **Audit History API:** Dedicated endpoint to retrieve the full version history of any `Post`.
6.  **User Attribution:** Automatically records the authenticated user performing changes in both audit logs (JaVers author, JPA createdBy/lastModifiedBy).
7.  **Custom JaVers Metadata:** Ability to add custom key-value pairs (like `tenant`) to JaVers audit commits.
8.  **DTO Pattern & MapStruct:** Clean API contracts and efficient entity-DTO mapping.
9.  **Spring Security Integration:** Basic authentication protecting API endpoints.
10. **Pagination Support:** Allows fetching lists of posts in pages.
11. **Configuration Driven:** Database and basic settings managed via `application.properties`.
12. **Clear Code Structure:** Well-organized packages for controllers, services, entities, repositories, mappers, and configuration.
