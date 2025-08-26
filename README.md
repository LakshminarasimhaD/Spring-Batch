🚀 Spring Batch CSV to Database with Email Notification
----------------------------------------------------------

This project demonstrates a Spring Batch application that imports employee data from a .csv file into a relational database (MySQL/Postgres/H2). It performs validation, transformation, persistence, and also sends a welcome email to each valid employee.

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
📌 Features
--------------

✅ Read employee data from a CSV file using FlatFileItemReader

✅ Validate records:

    ->Email format validation,
    
    ->Age must be 18 or older
    
✅ Transform raw CSV data (EmployeeCsv) into entity (Employee)

✅ Store valid employees in the database using Spring Data JPA

✅ Send welcome emails to employees after saving

✅ Skip invalid records gracefully (no failure for bad rows)

✅ Chunk-oriented processing (processes data in batches of 100 records for performance)

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

📂 Project Structure
----------------------

BatchConfig → Configures job, step, reader, processor, and writer.

EmployeeProcessor → Validates & transforms CSV data into Employee.

EmployeeWriter → Saves employees into DB & triggers email notifications.

EmailService → Sends emails using JavaMailSender.

EmployeeCsv DTO → Represents raw CSV row.

Employee Entity → Mapped entity stored in DB.

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

🛠️ Technologies Used
------------------------

Java 21 / 17

Spring Boot

Spring Batch

Spring Data JPA

Spring Mail (JavaMailSender)

MySQL / H2

Maven

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

⚙️ How It Works
----------------

Place the employees.csv file in the configured location.

Run the Spring Boot application.

Batch job (importEmployeeJob) starts and executes the step:

Read → Process → Write.

Valid employees get saved into the DB.

Welcome emails are sent to each valid employee.

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
📊 Sample CSV Format
-----------------------
employeeId,name,dob,state,departmentId,designation,salary,email

EMP00001,Rama Chandran,15-06-1990,Karnataka,D1,Developer,55000,ram.chandran@example.com

EMP00002,Smitha,01-12-2005,Delhi,D2,Analyst,45000,smith@invalid

O/P

✅ First record → valid (inserted + email sent).

❌ Second record → invalid (skipped due to age < 18 or bad email).
