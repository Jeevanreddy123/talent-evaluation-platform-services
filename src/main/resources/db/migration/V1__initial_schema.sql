
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
    id INTEGER PRIMARY KEY,
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
    id INTEGER PRIMARY KEY,
    file_name TEXT,
    file BLOB,
    technology TEXT,
    uploaded_by TEXT
);

CREATE TABLE questions (
    id INTEGER PRIMARY KEY,
    question_text TEXT NOT NULL,
    difficulty_level TEXT,
    technology TEXT
);
