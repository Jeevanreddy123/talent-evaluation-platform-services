-- Step 1: Create a new table without the 'email' column, including all other existing columns.
CREATE TABLE users_new (
    associate_id INTEGER PRIMARY KEY,
    username TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    first_name TEXT,
    last_name TEXT,
    enabled BOOLEAN NOT NULL,
    role TEXT,
    techstack TEXT,
    project_role TEXT
);

-- Step 2: Copy data from the old table to the new table
INSERT INTO users_new (associate_id, username, password, first_name, last_name, enabled, role, techstack, project_role)
SELECT associate_id, username, password, first_name, last_name, enabled, role, techstack, project_role
FROM users;

-- Step 3: Drop the old table
DROP TABLE users;

-- Step 4: Rename the new table to the original name
ALTER TABLE users_new RENAME TO users;