
```SQL

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

```

```SQL
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
```


```SQL

CREATE SEQUENCE IF NOT EXISTS public.student_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;



-- Table: public.student

-- DROP TABLE IF EXISTS public.student;

CREATE TABLE IF NOT EXISTS public.student
(
    id integer NOT NULL DEFAULT nextval('student_seq'::regclass),
    name character varying(255),
    father_name character varying(255),
    address text,
    adhaar_num BIGINT,
    dob DATE,
    contact_num character varying(255),
    email character varying(255),
    profile_pic BYTEA,
    comments text,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    CONSTRAINT student_pkey PRIMARY KEY (id),
    CONSTRAINT unique_email UNIQUE (email),
    CONSTRAINT unique_adhaar_num UNIQUE (adhaar_num),
    CONSTRAINT student_adhaar_num_check CHECK (adhaar_num >= '100000000000'::bigint AND adhaar_num <= '999999999999'::bigint)
);
	
--ALTER TABLE public.student ADD CONSTRAINT unique_email UNIQUE (email);
--ALTER TABLE public.student ADD CONSTRAINT unique_adhaar_num UNIQUE (adhaar_num);

```


