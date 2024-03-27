package com.example.technologiesieciowe1.entities;


import jakarta.persistence.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long userId;
    private String username;
    private String password;
    private String email;
    private String role;
    @Column(name = "full_name")
    private String fullName;

    @OneToMany(mappedBy = "user")
    private Set<Loan> loans;

    @OneToMany(mappedBy = "user")
    private Set<Review> reviews;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username cannot be null or empty");
        } else {
            this.username = username;
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password cannot be null or empty");
        } else {
            this.password = password;
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {

        if (email == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email cannot be null");
        }

        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        if (email.matches(emailRegex)) {
            this.email = email;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email address format");
        }
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        if (role == null || role.isEmpty()) {
            this.role = "ROLE_READER";
        } else {
            List<String> validRoles = Arrays.asList("ROLE_READER", "ROLE_LIBRARY_EMPLOYEE");

            if (validRoles.contains(role)) {
                this.role = role;
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user role");
            }
        }
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        if (fullName == null || fullName.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Full name cannot be null or empty");
        } else {
            this.fullName = fullName;
        }
    }

    public Long getUserId() {
        return userId;
    }

}


