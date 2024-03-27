package com.example.technologiesieciowe1.entities;


import com.example.technologiesieciowe1.google_books_api.BookDetail;
import jakarta.persistence.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.Set;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String isbn;
    private String title;
    private String author;
    private String publisher;
    private  Integer publishYear;
    private Integer availableCopies;
    private long countOfLoans;
    @Transient // the data field will not be mapped to the database
    private BookDetail bookDetail;
    @OneToMany(mappedBy = "book")
    private Set<Loan> loans;

    @OneToMany(mappedBy = "book")
    private Set<Review> reviews;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        if ((isbn == null) || (isbn.length() != 13) || (!isbn.matches("\\d+"))) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "ISBN must have 13 digits and contain only numbers");
        }
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Title cannot be null");
        }
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = Objects.requireNonNullElse(author, "Unknown");
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = Objects.requireNonNullElse(publisher, "Unknown");
    }

    public Integer getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(Integer publishYear) {
        if (publishYear < 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Publish year cannot be negative");
        }
        this.publishYear = publishYear;
    }

    public Integer getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(Integer availableCopies) {
        if (availableCopies < 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Available copies cannot be negative");
        }
        this.availableCopies = availableCopies;

    }

    public long getCountOfLoans() {
        return countOfLoans;
    }

    public void setCountOfLoans(long countOfLoans) {
        this.countOfLoans = countOfLoans;
    }
}
