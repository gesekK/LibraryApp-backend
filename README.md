**Project Description**

This documentation presents the technical details of the "Library App" project. This project was created to provide functionalities related to managing a book library and user accounts within the system. The application allows for book management, borrowing and returning books, adding reviews, managing users, and provides an API interface for communication.

**Project Objective**

The objective of the project is to develop a comprehensive system for managing a book library, enabling users to easily browse available books, borrow them, add reviews, and manage user accounts. The application aims to be user-friendly, intuitive, and provide comprehensive library management functionalities.

**Functional Scope**

The project encompasses the following functionalities:


    * Book Management: Adding, searching, editing, deleting books
    * User Management: Registration, login, account management
    * Borrowing and returning books, viewing borrowing history
    * Adding reviews to books
    * Providing an API interface for communication with the application

**Technologies Used**

The project was implemented using the following technologies and tools:


    * Backend: Java Spring Boot
    * Database: MySQL
    * Dependency Management: Maven
    * External API: Google Books API (for obtaining book information)
    * Security: Spring Security (for user authentication and authorization)
    * Version Control: Git
    
**Directory Structure and Files**

The project consists of the following main parts:

    -src/main/java: This directory contains all the backend source code written in Java.
    
      --com/example/technologiesieciowe1: Main package of the application.
      
        -configuration: Configuration for external API.
        -controllers: Controllers handling HTTP requests.
        -entities: Entities representing data structures in the database.
        -repositories: Repository interfaces for data access.
        -google book api: Representation of external API data structure.
        -services: Services containing business logic.
        -security: Security configurations, security filters, JWT Token classes.
        -src/main/resources: Backend resources such as configuration files, property files.

**Database**

The project utilizes a relational MySQL database for data storage. The database structure consists of the following tables:


    User Table:
      * userId: User identifier (primary key)
      * username: User's username.
      * password: User's password (hashed).
      * email: User's email address.
      * role: User's role in the system (e.g., ROLE READER, ROLE LIBRARY EMPLOYEE).
      * fullName: User's full name.


    Book Table:
      * id: Book identifier (primary key).
      * isbn: Book's ISBN number
      * title: Book's title.
      * author: Book's author.
      * publisher: Book's publisher.
      * publish year: Book's year of publication. 
      * available copies: Number of available copies of the book.
      * count of loans: Number of times the book has been borrowed.


    Loan Table:
      * loanId: Loan identifier (primary key).
      * loanDate: Date of loan.
      * returnDate: Planned return date.
      * status: Loan status.
      * userId: User identifier who borrowed the book (foreign key).
      * bookId: Book identifier that was borrowed (foreign key).

      
    Review Table:
      * reviewId: Review identifier (primary key).
      * content: Review content.
      * rating: Review rating.
      * userId: User identifier who wrote the review (foreign key).
      * bookId: Book identifier to which the review pertains (foreign key).

**API**

General Description

An API (Application Programming Interface) is a set of endpoints provided by the server, enabling communication and data exchange between clients and the server. In the case of this project, the API allows clients to perform operations related to book management, user management, loans, and adding reviews.

    POST /book/add - Adding a new book
    GET /book/getAll - Viewing all books
    GET /book/{id} - Searching for a book by id number
    GET /book/author/{author} - Searching for books by author
    GET /book/title/{title} - Searching for books by title
    DELETE /book/delete/{id} - Deleting a book from the database based on id number
    PATCH /book/update/{id} - Partially updating book data with a specified id
    PUT /book/update/all/{id} - Fully updating book data with a specified id
    GET /book/most-borrowed - Displaying a list of the 10 most borrowed books
    GET /book/details/{title} - Displaying additional information about a book based on the book title
    
    
    POST /login - Logging in a user based on username and password
    POST /user/logout - Logging out a user
    POST /user/add - Adding a new user
    GET /user/getAll - Viewing all users
    GET /user/{id} - Searching for a user by id number
    GET /user/username/{username} - Searching for a user by username
    GET /user/email/{email} - Searching for a user by email address
    DELETE /user/delete/{id} - Deleting a user from the database based on id number
    PATCH /user/update/{id} - Partially updating user data with a specified id
    PUT /user/update/all/{id} - Fully updating user data with a specified id
    GET /user/history/{userId} - Displaying the borrowing history of a user with a specified id
    
    
    POST /loan/add - Adding a new loan (ROLE LIBRARY EMPLOYEE)
    GET /loan/getAll - Viewing all loans
    GET /loan/{id} - Searching for a loan by id number
    POST /loan/borrow - Adding a new loan (ROLE READER)
    PUT /loan/return/{loanId} - Returning a borrowed book
    PUT /loan/approve/{loanId} - Confirming a book loan (ROLE LIBRARY EMPLOYEE)
    PUT /loan/confirm-return/{loanId} - Confirming the return of a book (ROLE LIBRARY EMPLOYEE)
    GET /loan/delayed-returns - Displaying a list of delayed book returns


    POST /reviews/add - Adding a new review
    GET /reviews/getAll - Viewing all reviews
    GET /reviews/book/{title} - Viewing a list of reviews for a book with the specified title
    GET /reviews/user/{userId} - Viewing a list of reviews written by a user with the specified user ID



**Security**

The project utilizes Spring Security tool to implement mechanisms securing the application. Spring Security provides comprehensive security features such as authentication, authorization, and access to individual functionalities based on JWT token.

**Security Features**

    * Authentication: The authentication mechanism verifies the user's identity based on provided authentication credentials, such as username and password.
    * Authorization: After user authentication, Spring Security checks whether the user has appropriate access permissions to the application resources. Permissions are determined based on user roles.
    * JWT Technology: JWT tokens are used for authenticating and authorizing users in the application. They are issued after successful authentication and contain information about the user's identity and permissions.


**Spring Security Configuration**

The configuration of Spring Security security mechanisms has been performed in the SecurityConfig class, where authentication and authorization rules have been defined. By using Spring Security, the application ensures secure access to resources and protects confidential user data from unauthorized access.


**Error Handling**

Various types of errors have been implemented in this application to provide correct and clear messages for users and ensure system stability. Below are the main types of errors and their handling methods:

    * Validation Errors: In case of data sent by the client, the application validates this data. If the data is invalid, an appropriate error message is returned, informing the user about the need to correct the entered data.
    * Resource Access Errors: If a user tries to access a resource to which they do not have appropriate permissions or if the requested resource does not exist, an appropriate error message is returned along with the relevant HTTP response code.
    * Uniform Error Response Formatting: All error messages are returned in a uniform format, facilitating their handling on the client side. This formatting includes information about the type of error, HTTP response code, and additional details that may be useful for the user.
