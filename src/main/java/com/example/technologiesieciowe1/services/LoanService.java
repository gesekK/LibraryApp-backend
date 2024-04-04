package com.example.technologiesieciowe1.services;

import com.example.technologiesieciowe1.entities.Book;
import com.example.technologiesieciowe1.entities.Loan;
import com.example.technologiesieciowe1.entities.User;
import com.example.technologiesieciowe1.repositories.BookRepository;
import com.example.technologiesieciowe1.repositories.LoanRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class LoanService {
    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;

    @Autowired
    public LoanService(LoanRepository loanRepository, BookRepository bookRepository) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
    }

    public Loan addLoan(Loan loan){
        return loanRepository.save(loan);
    }
    public Iterable<Loan> getAllLoans(){
        return loanRepository.findAll();
    }
    public Loan getLoanById(Long id){
        return loanRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan with id " + id + " not found"));
    }

    @Transactional
    public Loan borrowBook(User user, Book book) {
        long unreturnedBooksCount = loanRepository.countByUserAndStatus(user, "borrowed");
        if (unreturnedBooksCount >= 5) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User has reached the maximum number of borrowed books");
        }

        if (book.getAvailableCopies() <= 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Book is not available for borrowing");
        }
        Loan loan = new Loan();
        loan.setUser(user);
        loan.setBook(book);
        loan.setLoanDate(new Date(System.currentTimeMillis()));
        loan.setDueDate(calculateDueDate());
        loan.setStatus("waiting");

        book.setCountOfLoans(book.getCountOfLoans() + 1);
        book.setAvailableCopies(book.getAvailableCopies() - 1);

        loanRepository.save(loan);
        bookRepository.save(book);

        return loan;
    }
    @Transactional
    public void approveLoan(Long loanId) {
        Loan loan = loanRepository.findById(loanId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan not found"));
        loan.setStatus("borrowed");
        loanRepository.save(loan);
    }

    @Transactional
    public void confirmReturn(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan not found"));

        if (!loan.isReturnConfirmed()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Return not confirmed by user");
        }
        loan.setStatus("returned");

        Book book = loan.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);

        loanRepository.save(loan);
        bookRepository.save(book);

    }

    @Transactional
    public Iterable<Loan> getLoanHistoryByUserId(Long userId) {
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User ID cannot be null");
        }

        Iterable<Loan> loans = loanRepository.findByUserId(userId);

        if (!loans.iterator().hasNext()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No loan history found for user with ID: " + userId);
        }

        return loans;
    }

    private Date calculateDueDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 14); // 14 days to return
        return calendar.getTime();
    }
    @Transactional
    public void returnBook(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan not found"));

        if (loan.getReturnDate() != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Book has already been returned");
        }

        loan.setReturnDate(new Date());
        Date dueDate = loan.getDueDate();
        Date returnDate = loan.getReturnDate();
        loan.setReturnedLate(returnDate.after(dueDate));
        loan.setStatus("waiting");

        loan.setReturnConfirmed(true);

        loanRepository.save(loan);
    }

    public List<Loan> getDelayedReturns() {
        List<Loan> delayedReturns = new ArrayList<>();
        Iterable<Loan> allLoans = loanRepository.findAll();

        for (Loan loan : allLoans) {
            if (loan.getReturnDate() != null && loan.getReturnDate().after(loan.getDueDate())) {
                delayedReturns.add(loan);
            }
        }
        return delayedReturns;
    }
}
