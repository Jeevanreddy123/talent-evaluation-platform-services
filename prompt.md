## 1. High-Level Goal
 
Your task is to generate a complete, production-ready, single-module Spring Boot application for a "Talent Evaluation" service. The application will use a SQLite database, be secured with JWT, and include configurations for CI/CD and code quality analysis.
 
## 2. Core Technologies
 
- **Language & Framework**: Java 17, Spring Boot 3.1.5+
- **Build Tool**: Gradle 7.x+
- **Database**: SQLite with Flyway
- **API**: RESTful
- **Security**: Spring Security with JWT
- **File Handling**: Apache POI for Excel files
 

## 4. Gradle and Configuration
 
### `build.gradle`
Create a single build file with dependencies for Spring Web, Data JPA, Security, Actuator, Flyway, SQLite JDBC, Hibernate SQLite Dialect, JJWT, Apache POI, and Lombok.
 
### `application.properties`
Configure the application to run on port `8090` and connect to a local SQLite database file named `talent_evaluation.db`. Use the `org.hibernate.community.dialect.SQLiteDialect`. Enable Flyway's `baseline-on-migrate` and set a default `jwt.secret`.
 
## 5. Database Schema
 
### `db/migration/V1__initial_schema.sql`
Create the schema using SQLite-compatible syntax.
 
```sql
CREATE TABLE users (
    associate_id INTEGER PRIMARY KEY,
    username TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    first_name TEXT,
    last_name TEXT,
    email TEXT,
    enabled BOOLEAN NOT NULL,
    role TEXT
);
 
CREATE TABLE evaluations (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    candidate_name TEXT NOT NULL,
    skill TEXT,
    score INTEGER,
    evaluation_date TEXT,
    notes TEXT,
    resume BLOB,
    evaluator_id INTEGER,
    FOREIGN KEY (evaluator_id) REFERENCES users(associate_id)
);
 
CREATE TABLE excel_files (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    file_name TEXT,
    file BLOB,
    technology TEXT,
    uploaded_by TEXT
);
 
CREATE TABLE questions (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    question_text TEXT NOT NULL,
    difficulty_level TEXT,
    technology TEXT
);
```
 
## 6. Business Logic and API Layer Details
 
### 6.1. User & Authentication Feature
 
#### Entities & DTOs
- **`User` Entity**: `associateId` (Long, PK), `username`, `password`, `firstName`, `lastName`, `email`, `enabled` (boolean), `role`. Implement `UserDetails`.
- **`UserDto`**: Contains fields for updating a user: `associateId`, `firstName`, `lastName`, `email`, `role`.
- **`UserResponse`**: DTO for returning user data without the password.
- **`JwtRequest` / `JwtResponse`**: Standard models for JWT authentication request and response.
 
#### Repository
- **`UserRepository`**: `JpaRepository<User, Long>`. Add a method `findByUsername(String username)`.
 
#### Service (`UserService`)
- **`createUser(User user)`**: Encrypt the user's password using `BCryptPasswordEncoder` before saving. Throw an exception if the username already exists.
- **`getUser(String username)`**: Find a user by username and map to `UserResponse`.
- **`updateUser(UserDto userDto)`**: Find the user by `associateId` and update their details.
- **`getAllEvaluatorsGroupByStatus()`**: This is a placeholder; for now, just return a list of all users with the "EVALUATOR" role.
 
#### Security (`config` package)
- **`WebSecurityConfig`**:
  - Permit all requests to `/api/login`, `/api/user/register`, and `/actuator/health`.
  - All other requests must be authenticated.
  - Configure a `CorsFilter` to allow all origins (`@CrossOrigin` on controllers is also fine).
  - Use `SessionCreationPolicy.STATELESS`.
  - Add the `JwtRequestFilter` before the `UsernamePasswordAuthenticationFilter`.
- **`JwtUtils`**: Utility class to generate, validate, and extract username from a JWT.
- **`JwtRequestFilter`**: For each request, extract the JWT from the "Authorization" header. If valid, load `UserDetails` and set the authentication in the `SecurityContextHolder`.
 
#### Controller (`AuthenticateController` & `UserController`)
- **`POST /api/login`**: Use `AuthenticationManager` to authenticate. If successful, use `JwtUtils` to generate a token and return it in a `JwtResponse`.
- **`POST /api/user/register`**: Calls `userService.createUser()`.
- **`GET /api/user/{username}`**: Calls `userService.getUser()`.
- **`DELETE /api/user/{associateId}`**: Calls `userService.deleteUser()`.
 
### 6.2. Evaluation Management Feature
 
#### Entities & DTOs
- **`Evaluation` Entity**: `id` (Long, PK), `candidateName`, `skill`, `score` (Integer), `evaluationDate` (Date), `notes`, `resume` (byte[]), `evaluator` (`@ManyToOne` relationship to `User`).
- **`EvaluationDto`**: For creating an evaluation. Contains `candidateName`, `skill`, `evaluationDate` (String), and `evaluatorId`.
- **`EvaluateCandidateDto`**: For updating an evaluation. Contains `candidateId`, `score`, and `notes`.
 
#### Repository
- **`EvaluationRepository`**: `JpaRepository<Evaluation, Long>`. Add a method `findByEvaluatorAssociateId(Long associateId)`.
 
#### Service (`EvaluationService`)
- **`addEvaluation(Evaluation evaluation)`**: Saves a new evaluation. The controller will handle DTO-to-entity mapping.
- **`getAllCandidates()`**: Returns all evaluations.
- **`getCandidatesForEvaluator(Long associateId)`**: Uses the repository to find all evaluations for a given evaluator ID.
- **`uploadResume(MultipartFile file, Long candidateId)`**: Finds the evaluation by ID, gets the file's bytes, and saves it to the `resume` field.
- **`downloadResume(Long candidateId)`**: Retrieves the `resume` byte array from the evaluation.
- **`updateEvaluation(EvaluateCandidateDto dto)`**: Finds the evaluation by ID and updates its `score` and `notes`.
 
#### Controller (`EvaluationController`)
- All endpoints under `/api/evaluation`.
- **`POST /add-evaluation`**: Maps `EvaluationDto` to an `Evaluation` entity. Handles date string parsing (`yyyy-MM-dd` to `Date`).
- **`GET /getEvaluations/{associateId}`**: Calls the service to get evaluations for a specific user.
- **`POST /uploadResume/{candidateId}`**: Handles `MultipartFile` upload and calls the service.
- **`GET /downloadResume/{candidateId}`**: Calls the service and returns the `byte[]` with `Content-Disposition` header.
- **`PUT /update-evaluation`**: Calls the service to update an evaluation.
 
### 6.3. Question & File Management Feature
 
#### Entities
- **`Question` Entity**: `id` (Long, PK), `questionText`, `difficultyLevel`, `technology`.
- **`ExcelFile` Entity**: `id` (Long, PK), `fileName`, `file` (byte[]), `technology`, `uploadedBy`.
 
#### Repositories
- **`QuestionRepository`**: `JpaRepository<Question, Long>`. Add `deleteByTechnology(String technology)` and `findByDifficultyLevelAndTechnology(...)`.
- **`ExcelFileRepository`**: `JpaRepository<ExcelFile, Long>`. Add `deleteByTechnology(String technology)`.
 
#### Helper (`ExcelHelper`)
- **`checkExcelFormat(MultipartFile file)`**: Returns `true` if the file is of type `application/vnd.openxmlformats-officedocument.spreadsheetml.sheet`.
- **`excelToQuestions(InputStream is, String technology)`**: Parses an `.xlsx` file using Apache POI. Reads rows and maps them to a `List<Question>`.
 
#### Service (`QuestionService` & `ExcelFileService`)
- **`ExcelFileService.saveExcel(...)`**: Saves the `ExcelFile` entity.
- **`ExcelFileService.deleteFileByTechnology(String technology)`**: Deletes the file record from the DB.
- **`QuestionService.save(MultipartFile file, ...)`**: Uses `ExcelHelper` to parse the file and saves the resulting list of questions.
- **`QuestionService.deleteQuestionByTechnology(String technology)`**: Deletes all questions for a given technology.
 
#### Controllers (`ExcelFileController` & `QuestionController`)
- **`POST /api/file`**:
  - **Business Logic**: This endpoint must be transactional. First, call `excelFileService.deleteFileByTechnology()`. Second, call `questionService.deleteQuestionByTechnology()`. Third, parse the new file using `ExcelHelper`. Finally, save the new questions and the new file entity.
- **`DELETE /api/file/{technology}`**: Calls both delete services to remove the file and its associated questions.
- **`GET /api/question/level/{level}/technology/{technology}`**: Calls the service to filter questions.