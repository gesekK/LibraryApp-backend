# 📚 LibraryApp – Backend

## 📌 Overview
**LibraryApp** is a backend application designed to manage a book library and user accounts.  
It provides:
- Book management
- Borrowing and returning books
- Adding reviews
- User account management
- A REST API for external communication

Built with **Java Spring Boot**, it integrates with **MySQL** and uses **Spring Security** for authentication and authorization.

---

## 🚀 Features

### 📖 Book Management
- Add new books
- Search books by ID, author, or title
- Edit book information (full or partial update)
- Delete books
- View the 10 most borrowed books
- Fetch additional details via **Google Books API**

### 👤 User Management
- User registration & login
- Account update & deletion
- View all users or search by ID, username, or email
- View borrowing history

### 📦 Loan Management
- Borrow books (reader role)
- Approve loans (library employee role)
- Return books
- View all loans or search by ID
- View delayed returns

### 📝 Reviews
- Add reviews for books
- View reviews by book title or user ID

---

## 🛠 Technologies Used
- **Backend:** Java Spring Boot
- **Database:** MySQL
- **Build Tool:** Maven
- **External API:** Google Books API
- **Security:** Spring Security + JWT
- **Version Control:** Git

---

## 🗄 Database Schema

### **User**
| Column   | Type    | Description |
|----------|---------|-------------|
| userId   | PK      | User ID |
| username | String  | Username |
| password | String  | Hashed password |
| email    | String  | Email address |
| role     | String  | ROLE_READER / ROLE_LIBRARY_EMPLOYEE |
| fullName | String  | Full name |

### **Book**
| Column          | Type    | Description |
|-----------------|---------|-------------|
| id              | PK      | Book ID |
| isbn            | String  | ISBN number |
| title           | String  | Title |
| author          | String  | Author |
| publisher       | String  | Publisher |
| publish_year    | Int     | Year of publication |
| available_copies| Int     | Available copies |
| count_of_loans  | Int     | Times borrowed |

### **Loan**
| Column   | Type    | Description |
|----------|---------|-------------|
| loanId   | PK      | Loan ID |
| loanDate | Date    | Loan date |
| returnDate| Date   | Planned return date |
| status   | String  | Loan status |
| userId   | FK      | User who borrowed |
| bookId   | FK      | Book borrowed |

### **Review**
| Column   | Type    | Description |
|----------|---------|-------------|
| reviewId | PK      | Review ID |
| content  | String  | Review content |
| rating   | Int     | Review rating |
| userId   | FK      | Review author |
| bookId   | FK      | Reviewed book |

---

## 📌 API Endpoints

### 📖 Book Management
| Method | Endpoint | Description |
|--------|----------|-------------|
| **POST** | `/book/add` | Add a new book to the library |
| **GET** | `/book/getAll` | Retrieve a list of all books |
| **GET** | `/book/{id}` | Get details of a book by its ID |
| **GET** | `/book/author/{author}` | Search books by author |
| **GET** | `/book/title/{title}` | Search books by title |
| **DELETE** | `/book/delete/{id}` | Delete a book by ID |
| **PATCH** | `/book/update/{id}` | Partially update book information |
| **PUT** | `/book/update/all/{id}` | Fully update book information |
| **GET** | `/book/most-borrowed` | Get a list of the 10 most borrowed books |
| **GET** | `/book/details/{title}` | Retrieve additional book details using **Google Books API** |

---

### 👤 User Management
| Method | Endpoint | Description |
|--------|----------|-------------|
| **POST** | `/login` | Authenticate a user with username and password |
| **POST** | `/user/logout` | Log out the current user |
| **POST** | `/user/add` | Register a new user |
| **GET** | `/user/getAll` | Retrieve a list of all users |
| **GET** | `/user/{id}` | Get user details by ID |
| **GET** | `/user/username/{username}` | Search a user by username |
| **GET** | `/user/email/{email}` | Search a user by email address |
| **DELETE** | `/user/delete/{id}` | Delete a user by ID |
| **PATCH** | `/user/update/{id}` | Partially update user data |
| **PUT** | `/user/update/all/{id}` | Fully update user data |
| **GET** | `/user/history/{userId}` | Get borrowing history of a specific user |

---

### 📦 Loan Management
| Method | Endpoint | Description |
|--------|----------|-------------|
| **POST** | `/loan/add` | Add a new loan (**ROLE_LIBRARY_EMPLOYEE**) |
| **GET** | `/loan/getAll` | Retrieve a list of all loans |
| **GET** | `/loan/{id}` | Get loan details by ID |
| **POST** | `/loan/borrow` | Borrow a book (**ROLE_READER**) |
| **PUT** | `/loan/return/{loanId}` | Return a borrowed book |
| **PUT** | `/loan/approve/{loanId}` | Approve a book loan (**ROLE_LIBRARY_EMPLOYEE**) |
| **PUT** | `/loan/confirm-return/{loanId}` | Confirm the return of a book (**ROLE_LIBRARY_EMPLOYEE**) |
| **GET** | `/loan/delayed-returns` | Get a list of delayed book returns |

---

### 📝 Reviews
| Method | Endpoint | Description |
|--------|----------|-------------|
| **POST** | `/reviews/add` | Add a new review for a book |
| **GET** | `/reviews/getAll` | Retrieve all reviews |
| **GET** | `/reviews/book/{title}` | Get reviews for a specific book by title |
| **GET** | `/reviews/user/{userId}` | Get reviews written by a specific user |
---

## 🔒 Security

Implemented with **Spring Security** + **JWT**:
- **Authentication**: Validates credentials (username & password).
- **Authorization**: Grants access based on user roles.
- **JWT Tokens**: Issued upon login, containing user identity & permissions.

**Security Configuration**: Defined in `SecurityConfig`, including access rules for endpoints and JWT filters.

---

## ⚠ Error Handling
The application includes:
- **Validation Errors** – Returned when input data fails validation.
- **Authentication/Authorization Errors** – Returned when user credentials are invalid or access is denied.
- **Resource Not Found** – Returned when requested resources are missing.

Error responses are **clear and user-friendly** to ensure good UX.

---

## 📄 License
This project is licensed under the MIT License.

---

👨‍💻 **Author:** *Katarzyna Gesek*  
📅 **Version:** 1.0
