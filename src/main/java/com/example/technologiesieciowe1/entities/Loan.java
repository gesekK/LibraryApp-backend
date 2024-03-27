package com.example.technologiesieciowe1.entities;

import jakarta.persistence.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

@Entity
public class Loan {

    private static Date currentDate = new Date(System.currentTimeMillis());

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long loanId;
    private String status;
    private boolean returnConfirmed;
    private boolean returnLate;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public static Date getCurrentDate() {
        return currentDate;
    }

    public static void setCurrentDate(Date currentDate) {
        Loan.currentDate = currentDate;
    }

    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

    public String getStatus() {
        return status;
    }

    public Date getLoanDate() {
        return loanDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    @Temporal(TemporalType.DATE)
    private Date loanDate;

    @Temporal(TemporalType.DATE)
    private Date dueDate;

    @Temporal(TemporalType.DATE)
    private Date returnDate;


    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() { return user; }

    public void setUser(User user) {
        this.user = user;
    }

    public void setLoanDate(Date loanDate) {
        if (loanDate.after(currentDate)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Loan date cannot be set to a future date");
        }
        this.loanDate = loanDate;

    }

    public void setDueDate(Date dueDate) {
        if (dueDate != null && dueDate.before(loanDate)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Due date cannot be before loan date");
        }
        this.dueDate = dueDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }


    public void setReturnDate(Date returnDate) {
        if (returnDate != null) {
            if (returnDate.before(loanDate)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Return date cannot be before loan date");
            }
            if (returnDate.after(currentDate)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Return date cannot be in the future");
            }
        }
        this.returnDate = returnDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isReturnConfirmed() {
        return returnConfirmed;
    }

    public void setReturnConfirmed(boolean returnConfirmed) {
        this.returnConfirmed = returnConfirmed;
    }

    public void setReturnedLate(boolean b) {
    }
}