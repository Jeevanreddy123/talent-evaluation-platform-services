-- This migration aligns the database schema with the updated Java entities.
-- It adds missing columns to the 'users' table and refactors the 'evaluations' table.
-- IMPORTANT: Review your V2-V5 migrations and adjust this script if those migrations
-- have already made some of these changes.

-- Add missing columns to the 'users' table to match the User entity.
-- !! CHECKPOINT: Before running, ensure these columns were not already added in V2-V5.
-- If a column already exists, remove or comment out the corresponding ALTER TABLE line.
ALTER TABLE users ADD COLUMN tech_stack TEXT;
ALTER TABLE users ADD COLUMN project_role TEXT;
ALTER TABLE users ADD COLUMN updated_by TEXT;


-- Refactor the 'evaluations' table to match the new Evaluation entity.
-- This uses a "recreate and copy" pattern which is safe for SQLite.
-- !! CHECKPOINT: This script assumes the 'evaluations' table still has the structure from V1.
-- If V2-V5 changed the 'evaluations' table, you MUST adjust the INSERT statement in Step 3.

-- Step 1: Rename the current table to preserve its data.
ALTER TABLE evaluations RENAME TO evaluations_old;

-- Step 2: Create the new 'evaluations' table with the correct, final schema.
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

-- Step 3: Migrate data from the old table to the new one.
-- !! CHECKPOINT: This is the most critical part to verify. The columns in the SELECT statement
-- (id, skill, candidate_name, etc.) must exist in the 'evaluations' table as of V5.
-- Adjust the column names to match your current schema.
INSERT INTO evaluations (candidate_id, candidate_stack, first_name, evaluation_details, resume_file, associate_id)
SELECT id, skill, candidate_name, notes, resume, evaluator_id
FROM evaluations_old;

-- Step 4: Drop the old, temporary table.
DROP TABLE evaluations_old;