# college-app

| Technology  | Specification |
| ------------- | ------------- |
| Java  | Java 17  |
| Framework  | Spring boot  |
| DB  | Postgres  |


### phase-1
> DONE = wildfly 
> 
> DONE = postgres + mysql
> 
> DONE = java.util.logging - log management- separate log
> 
> DONE = filter, sort & pagination params
> 
> DONE = springboot crud
> 
> in progress = mockito test case
> 
> spring cache
---------------------------------------
> microservices
> 
> pomparent after 2 services
> 
> @Slf4j - log management- separate log ( any one of the services )
> 
> openapi
> 
> flywayDB
> 
> apigateway
> 
> kafka
---------------------------------------
> authentication & authorization  ( max session = 1 )
> 
> own user account
> 
> github
> 
> gmail
> 
> facebook
> 
> linkedin
> 
> webhooks
---------------------------------------
> deployment
> 
> Github pipeline
> 
> AWS
> 
> jenkins
> 
> docker
> 
> kubernetes
______________________________________________________________

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
##### SERVICE 2 - STUDENT SERVICE
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
photo
comments
created_at
updated_at
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

#### api requests, responses and pages design
##### login / logout
##### page-1
##### page-2
##### page-3
##### page-4







