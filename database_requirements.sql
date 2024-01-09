

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




CREATE SEQUENCE IF NOT EXISTS public.department_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;



-- Table: public.department

-- DROP TABLE IF EXISTS public.department;

CREATE TABLE IF NOT EXISTS public.department
(
    id integer NOT NULL DEFAULT nextval('department_seq'::regclass),
    comments character varying(255),
    name character varying(255),
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    CONSTRAINT department_pkey PRIMARY KEY (id),
    CONSTRAINT unique_department_name UNIQUE (name)
);
	
--ALTER TABLE public.department ADD CONSTRAINT unique_department_name UNIQUE (name);


table : student
