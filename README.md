## Employee Management System (Spring MVC + Spring Security)
This project is a secure, role-based web application developed using the Spring Framework. Its primary goal is to provide a robust system for managing employee records with strict access control based on user roles (Admin and User).

✨ Key Technologies Used
Backend Framework: Spring MVC

Security: Spring Security (Role-Based Access Control)

Database: MySQL (JDBC)

Encryption: BCryptPasswordEncoder for secure password hashing

Frontend/View: JSP (JavaServer Pages)

Server: Apache Tomcat 9.x

Build Tool: Maven

🎯 Project Goals
The system implements the fundamental CRUD (Create, Read, Update, Delete) operations for employee records while ensuring that access to these operations is strictly governed by user permissions.

🛡️ Security Implementation (Spring Security)
The core strength of this application lies in its comprehensive security setup:

Authentication (Login):

Uses CustomUserDetailsService to fetch user details (username, password hash, roles) from the MySQL database.

Passwords are never stored in plain text. BCryptPasswordEncoder is used for one-way hashing to securely verify user credentials against the stored hash in the database.

Authorization (Access Control):

Access rules are defined using hasRole() and hasAnyRole() expressions within the Spring Security configuration.

ADMIN Role: Authorized to perform all CRUD operations: Add (/save), Update (/update), Delete (/delete).

USER Role: Limited to viewing employee details (/list, /view/{id}).

CSRF Protection:

Cross-Site Request Forgery (CSRF) protection is enabled by default. A hidden CSRF token is mandatory in all POST forms (Add Employee, Update Employee) to prevent malicious attacks, thus securing data integrity.

