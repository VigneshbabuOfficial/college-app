PROJECT PLAN

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

> - [x] Implementation of Service 1 ( Department Service )
>
> - [x] Implementation of Service 2 ( STUDENT SERVICE )
>
> - [ ] Implementation of Service 3 ( FACULTY SERVICE )
>
> - [ ] Implementation of Service 4 ( ACCOUNT SERVICE )
> 
> - [ ] Implementation of Microservice Architecture for STUDENT SERVICE & ACCOUNT SERVICE & FACULTY SERVICE </br>
> ( Faculty service should send request to ACCOUNT service with facultyId as request param and also with other optional params ( like department=CSE&pass_out_year=2020 ) and for the STUDENT service studentId should be sent withoud any filter params and make sure not to allow to view / edit / delete other student records )
>  
> - [ ] Implementation of Basic JWT Authentication.
> 
> - [ ] Implementation of JWT Authentication with Microservice Arch,.
> 
> - [ ] Implementation of Basic OAuth Authentication.
> 
> - [ ] Implementation of OAuth Authentication with Microservice Arch,.
> 
> - [ ] Implementation of Basic AuthO Authentication.
> 
> - [ ] Implementation of AuthO Authentication with Microservice Arch,.
>
> - [ ] Implementation of Microservice Architecture for Attendance, Exam and Fee services should be invoked from student service only. like student/accounts , student/exam .... )
>
> - [ ] Implementation of EXAM SERVICE and should support the uploading the excel sheet of students exam mark and same to be updated for all the students by scheduler service.


