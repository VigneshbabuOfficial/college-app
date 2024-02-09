PROJECT PLAN


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

----------------------------------

#### PROJECT OVERVIEW

> Faculty should log into the application and session can be maintained in the Faculty service. From the Faculty service using M2M token all other services Accounts, Attendance, Exams and Fee service should be communicated.
>
> Student should log into the application and session can be maintained in the Student service. From the Student service using M2M token all other services Accounts, Attendance, Exams and Fee service should be communicated based on the Student ID.
>
> Exam support service to be implemented to update the exam marks automatically by uploading the exam result as a excel sheet like a Scheduler service.
>
> Accounts service - Springboot - gradle - mysql - flyway - jar </br> 
> Attendance service - Springboot - gradle - postgres - flyway - jar </br>
> Exam service - Springboot - maven - postgres - flyway - jar </br>
> Exam support service - Springboot - maven - postgres - flyway - jar </br>
> Fee Service - Springboot - maven - mysql - flyway - jar </br>


<img width="755" alt="image" src="https://github.com/VigneshbabuOfficial/college-app/assets/70185865/1484a11d-5643-4a1b-a23c-08d88addccce">

-----------------------------------------

#### TASKS

> - [x] Implementation of Service 1 ( DEPARTMENT Service )
>
> - [x] Implementation of Service 2 ( STUDENT SERVICE )
>
> - [X] Implementation of Service 3 ( FACULTY SERVICE )
>
> - [ ] Implementation of Cache mechanism
>
> - [ ] Microservice Architecture Implementation ( api, gateway, ELK, Kafka, ... )
>
> - [ ] Implementation of Basic JWT Authentication.
> 
> - [ ] Implementation of JWT Authentication with Microservice Arch,.
> 
> - [ ] Implementation of Basic OAuth Authentication.
> 
> - [ ] Implementation of Basic OAuth Authentication with Microservice Arch,.
> 
> - [ ] Implementation of Basic AuthO Authentication.
> 
> - [ ] Implementation of AuthO Authentication with Microservice Arch,.
>       
> - [ ] Implementation of Service 4 ( ACCOUNT SERVICE )
> 
> - [ ] Implementation of Microservice Architecture for STUDENT SERVICE & ACCOUNT SERVICE & FACULTY SERVICE </br>
> ( Faculty service should send request to ACCOUNT service with facultyId as request param and also with other optional params ( like department=CSE&pass_out_year=2020 ) and for the STUDENT service studentId should be sent withoud any filter params and make sure not to allow to view / edit / delete other student records )
>
> - [ ] Implementation of EXAM SERVICE and should support the uploading the excel sheet of students exam mark and same to be updated for all the students by scheduler service.
>        
> - [ ] Exam support service to be implemented to update the exam marks automatically by uploading the exam result as a excel sheet like a Scheduler service.
>
> - [ ] Implemention of Attendance Service
>       
> - [ ] Implementation of Microservice Architecture for Attendance, Exam and Fee services should be invoked from student service only. like student/accounts , student/exam .... )

--------------------------------------



