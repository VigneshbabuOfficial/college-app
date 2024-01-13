# college-app

| Technology  | Specification |
| ------------- | ------------- |
| Java  | Java 17  |
| Framework  | Spring boot  |
| DB  | Postgres  |


> [Documentation](https://documenter.getpostman.com/view/25187704/2s9YsJCCkX)

<img width="755" alt="image" src="https://github.com/VigneshbabuOfficial/college-app/assets/70185865/b465d37a-69bf-40bd-a0f6-6cceac608a62">


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
> DONE = mockito test case
> 
> spring cache
---------------------------------------
> Authenitication Service
> 
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
> microservices
> 
> openapi
> 
> flywayDB
> 
> pomparent after 2 services
> 
> @Slf4j - log management- separate log ( any one of the services )
>
> ELK
>  
> apigateway
>
> JMS & Active MQ
> 
> kafka
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

>  Teacher View

##### HOME PAGE ( page-1 afer loggedin ) 
<img width="576" alt="image" src="https://github.com/VigneshbabuOfficial/college-app/assets/70185865/47bd7494-cc68-412d-9f61-03a1c9637fbc">

##### page-2 - Department 
> showing All the Departments and choosing Department -> choose year of passouts -> listing all the Students as table row ( name, father name, email, contact num ) and hovering over each record should show details with profile pic

##### page-3 - Students 
> filter ( department , year of pass out ) - listing the students ( by default show the data as recent to old )
> 
> if any individual student is clicked then Student View should be shown
> 
> Save & Next button to enable Teacher enter the student details

##### page-4 - Accounts 
> filter ( department , year of pass out ) - listing the student accounts ( by default show the data as recent to old )
>
> Save & Next button to enable Teacher enter the student details

##### page-5 - Exams 
> filter ( department , year of pass out, Semester( 1-8 ), Subject ) - listing the student exam details ( by default show the data as recent to old )
>
> Save & Next button to enable Teacher enter the student exam details

##### page-6 - Attendance 
> filter ( department , year of pass out, Semester( 1-8 ) ) - listing the student attendance details ( by default show the data as recent to old )
>
> Save & Next button to enable Teacher enter the student attendance details
>
> Take Attendance

##### page-7 - Fee 
> filter ( department , year of pass out ) - listing the student accounts ( by default show the data as recent to old )
>
> Save & Next button to enable Teacher enter the student fee details

##### settings - edit font type & size
##### logout

-------------------------------- 

> Student View
##### HOME PAGE ( page-1 afer loggedin )
> showing the student personal info ( edit & save )
>
> profile pic edit in popup
<img width="576" alt="image" src="https://github.com/VigneshbabuOfficial/college-app/assets/70185865/53b163e6-b549-4200-81a5-cc246b5761eb">

##### page-2 - Accounts - showing current student accounts details ( edit & save )
##### page-3 - Exams - showing current student exams details ( edit & save )
##### page-4 - Attendance - showing current student attendance details ( edit & save )
##### page-5 - Fee - showing current student fee details ( edit & save )
##### settings - edit font type & size
##### logout


