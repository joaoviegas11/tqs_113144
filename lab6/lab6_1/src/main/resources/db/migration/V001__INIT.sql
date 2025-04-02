CREATE TABLE students (
    mec SERIAL PRIMARY KEY,
    ncc INT NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    course VARCHAR(255) NOT NULL,
    academic_degree VARCHAR(255) NOT NULL
);
