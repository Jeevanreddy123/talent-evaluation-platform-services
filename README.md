# talent-evaluation-platform-services
 
A backend service built with Spring Boot to manage and conduct technical talent evaluations. This platform allows for managing evaluation questions, conducting evaluations for candidates, and storing evaluation results, including resumes.

## Features

- **User Authentication**: Secure JWT-based authentication and authorization.
- **Question Management**:
    - Bulk import of questions from Excel files (`.xlsx`).
    - Questions categorized by technology and difficulty level.
- **Candidate Evaluation**:
    - Record evaluation details for candidates, including scores and notes.
    - Assign evaluators to evaluations.
- **Resume Management**: Upload and download candidate resumes.
- **Database Migrations**: Uses Flyway to manage database schema evolution.

## Technologies Used

- **Backend**: Java, Spring Boot
- **Data Persistence**: Spring Data JPA, Hibernate, SQLite
- **Security**: Spring Security, JSON Web Tokens (JWT)
- **Database Migration**: Flyway
- **Build Tool**: Gradle
- **Libraries**: Apache POI (for Excel processing)

## Getting Started

### Prerequisites

- JDK 17 or later
- Gradle 7.x or later

### Installation & Running the Application

1.  **Clone the repository** and navigate into the directory.
2.  **Build and run the application** using the Gradle wrapper:
    ```bash
    ./gradlew bootRun
    ```
The application will start on `http://localhost:8090`.

## Configuration

The main configuration is located in `src/main/resources/application.properties`. The application is pre-configured to use an embedded SQLite database (`vibe.db`), which will be created in the project root.

For security, the JWT secret key is defined in this file. **Important:** For a production environment, you must use a strong, securely stored secret, rather than the one committed in the repository.
