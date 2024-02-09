
### Database Table Design DOCs

##### filter format
> filter=name:=:EEE,comments:=:test <br/>
> ORfilter=comments:=:test,id:=:3,name:=:EEE

##### sort format
> sort=name:asc

##### pagination format 
> page=1 & limit=20

##### fields 
> fields=name

=================================================================


###  tables design

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

##### SERVICE 1 - DEPARTMENT SERVICE
``` 
table : departments
columns :
id
name
name values > me,ece,eee,cse,it
comments
created_at
updated_at
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

##### SERVICE 2 - STUDENT SERVICE & FACULTY SERVICE
```
table : students
columns :
id
name
father_name
address
adhaar_num
dob
contact_num
email
profile_pic
comments
created_at
updated_at
```

```SQL
-- DROP TABLE IF EXISTS faculty;

CREATE TABLE faculty
(
    id 					INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name 				VARCHAR(255) NOT NULL,
    father_name 		VARCHAR(255) NOT NULL,
    address 			TEXT,
    adhaar_num 			BIGINT(12),
    dob 				DATE,
    contact_num 		VARCHAR(255) NOT NULL,
    email 				VARCHAR(255) NOT NULL,
    profile_pic 		BLOB,
    comments 			TEXT,
    created_at 			TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at 			TIMESTAMP,
    UNIQUE KEY faculty_email_uk (email),
    UNIQUE KEY faculty_adhaar_num_uk (adhaar_num)
);
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

```SQL

MY SQL TABLE FACULTY

-- DROP TABLE IF EXISTS faculty;

CREATE TABLE IF NOT EXISTS faculty
(
    id 					INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name 				VARCHAR(255) NOT NULL,
    father_name 		VARCHAR(255) NOT NULL,
    address 			TEXT,
    adhaar_num 			BIGINT(12),
    dob 				DATE,
    contact_num 		VARCHAR(255) NOT NULL,
    email 				VARCHAR(255) NOT NULL,
    profile_pic 		BLOB,
    comments 			TEXT,
    created_at 			TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at 			TIMESTAMP,
    UNIQUE KEY faculty_email_uk (email),
    UNIQUE KEY faculty_adhaar_num_uk (adhaar_num),
);
```

##### SERVICE 3 - STUDENT_ACCOUNTS SERVICE
```
table : student_accounts
columns :
id
student
user_name
password(enc) -->> db level encryption
department
is_hosteller
academic_year(string) =>> 2012-2016
current_studying_year
current_studying_semester
id_card(blob data file)
comments
created_at
updated_at
```
##### SERVICE 4 - STUDENTS_EXAMS SERVICE
```
table : student_exams
columns :
id
student
exams_attended
exams_passed
exams_failed
comments
subjects (json)
created_at
updated_at
```
```
*** sample data *****
{
   "passed":
	 { 
      "1":
        [
          {
            "subject":"english-1",
            "semester":"1",
            "grade":"a"
          },
          {
            "subject":"maths-1",
            "semester":"1",
            "grade":"b"
          }
        ],
      "2":
        [
          {
            "subject":"english-1",
            "semester":"1",
            "grade":"a"
          },
          {
            "subject":"maths-1",
            "semester":"1",
            "grade":"b"
          }
        ]  
    },
   "failed":
   { 
      "1":
        [
          {
            "subject":"physics-1",
            "semester":"1",
            "grade":"u"
          },
          {
            "subject":"chemistry-1",
            "semester":"1",
            "grade":"u"
          }
        ],
      "2":
        [
          {
            "subject":"physics-1",
            "semester":"1",
            "grade":"u"
          },
          {
            "subject":"chemistry-1",
            "semester":"1",
            "grade":"u"
          }
        ]  
    }
}
```
##### SERVICE 5 - STUDENTS_ATTENDANCE SERVICE
```
table : attendance
columns :
id
student
comments
attendance
created_at
updated_at
```
```
***** SAMPLE DATA ****
{
  "present": {
    "2012": {
      "month": "jan",
      "dates": [
        1,
        2,
        3,
        4,
        5,
        6
      ]
    },
    "2013": {
      "month": "jan",
      "dates": [
        1,
        2,
        3,
        4,
        5,
        6
      ]
    },
    "2014": {
      "month": "jan",
      "dates": [
        1,
        2,
        3,
        4,
        5,
        6
      ]
    },
    "2015": {
      "month": "jan",
      "dates": [
        1,
        2,
        3,
        4,
        5,
        6
      ]
    },
    "2016": {
      "month": "jan",
      "dates": [
        1,
        2,
        3,
        4,
        5,
        6
      ]
    }
  },
  "leave": {
    "2012": {
      "month": "jan",
      "dates": [
        1,
        2,
        3,
        4,
        5,
        6
      ]
    },
    "2013": {
      "month": "jan",
      "dates": [
        1,
        2,
        3,
        4,
        5,
        6
      ]
    },
    "2014": {
      "month": "jan",
      "dates": [
        1,
        2,
        3,
        4,
        5,
        6
      ]
    },
    "2015": {
      "month": "jan",
      "dates": [
        1,
        2,
        3,
        4,
        5,
        6
      ]
    },
    "2016": {
      "month": "jan",
      "dates": [
        1,
        2,
        3,
        4,
        5,
        6
      ]
    }
  }
}

```
##### SERVICE 6 - STUDENTS_FEES SERVICE
```
table : fee_management
columns :
id
student
total_fee
total_fee_paid
total_fee_due
year_1_fee_paid
year_1_fee_due
year_1_hostel_fee_paid
year_1_hostel_fee_due
year_2_fee_paid
year_2_fee_due
year_2_hostel_fee_paid
year_2_hostel_fee_due
year_3_fee_paid
year_3_fee_due
year_3_hostel_fee_paid
year_3_hostel_fee_due
year_4_fee_paid
year_4_fee_due
year_4_hostel_fee_paid
year_4_hostel_fee_due
comments
created_at
updated_at
```







