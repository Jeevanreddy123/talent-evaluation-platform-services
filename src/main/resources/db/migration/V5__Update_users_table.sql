-- Step 1: Create a new table with the desired schema changes:
-- - Remove 'enabled' column
-- - Rename 'techstack' to 'tech_stack'
-- - Add 'updated_by' column
CREATE TABLE users_new (
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

-- Step 2: Copy data from the old table to the new table, mapping the renamed column
INSERT INTO users_new (associate_id, username, password, first_name, last_name, role, tech_stack, project_role)
SELECT associate_id, username, password, first_name, last_name, role, techstack, project_role
FROM users;

-- Step 3: Drop the old table
DROP TABLE users;

-- Step 4: Rename the new table to the original name
ALTER TABLE users_new RENAME TO users;