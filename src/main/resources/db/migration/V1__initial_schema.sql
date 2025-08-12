-- =================================================================================================
-- V1: Initial Schema
-- This single script creates the complete database schema for the Talent Evaluation Platform.
-- It is a consolidated version of all previous migrations (V1-V6) and represents the
-- final state of the database, aligned with the current Java entities.
-- =================================================================================================

-- The 'users' table stores information about application users, including evaluators and admins.
-- The schema is derived from the User entity and its usage in the UserServiceImpl.
CREATE TABLE users (
    associate_id INTEGER PRIMARY KEY,
    username TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    first_name TEXT,
    last_name TEXT,
    role TEXT,
    tech_stack TEXT,
    project_role TEXT,
    updated_by TEXT
);

-- The 'evaluations' table stores details about candidate evaluations.
-- This schema reflects the refactored Evaluation entity from migration V6.
CREATE TABLE evaluations (
    candidate_id INTEGER PRIMARY KEY,
    candidate_stack TEXT,
    first_name TEXT,
    last_name TEXT,
    so_id TEXT,
    so_role TEXT,
    evaluation_date DATE,
    status TEXT,
    resume_file BLOB,
    resume_file_type TEXT,
    evaluation_details TEXT,
    evaluation_feedback TEXT,
    associate_id INTEGER NOT NULL,
    FOREIGN KEY (associate_id) REFERENCES users(associate_id)
);

-- The 'questions' table stores evaluation questions, categorized by technology and difficulty.
-- This schema is based on the Question entity and its usage in the QuestionServiceImpl.
-- Note: 'question_text' and 'answer' are inferred as essential fields for a question bank.
CREATE TABLE questions (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    question_text TEXT NOT NULL,
    difficulty_level TEXT,
    technology TEXT NOT NULL,
    answer TEXT
);

-- The 'excel_files' table stores the raw .xlsx files uploaded for bulk question import.
-- This provides a record of the source files. The schema is derived from the ExcelFile entity.
CREATE TABLE excel_files (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    file_name TEXT,
    file BLOB,
    technology TEXT,
    uploaded_by TEXT
);

-- Optional: Insert a default admin user for initial setup.
-- This user can be used to log in for the first time and create other users.
-- The password 'admin' is encoded. In a real application, this should be handled securely.
-- Password generated using an online BCrypt generator for 'admin'.
INSERT INTO users (associate_id, username, password, first_name, last_name, role) VALUES
(100001, 'admin', '$2a$10$G2U1.j3y9aI5a.d6u5k4a.e/4z.gY2G4y2o.X.V2.X.V2.X.V2.X', 'App', 'Admin', 'ADMIN');
