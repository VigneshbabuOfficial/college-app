

-- Database: college_students_db

-- DROP DATABASE IF EXISTS college_students_db;

CREATE DATABASE college_students_db
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'English_India.1252'
    LC_CTYPE = 'English_India.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;


table : department
table : student
