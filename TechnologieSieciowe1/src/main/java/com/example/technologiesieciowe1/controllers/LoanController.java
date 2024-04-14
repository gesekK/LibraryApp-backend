package com.example.technologiesieciowe1.controllers;

import com.example.technologiesieciowe1.entities.Book;
import com.example.technologiesieciowe1.entities.Loan;
import com.example.technologiesieciowe1.entities.User;
import com.example.technologiesieciowe1.services.BookService;
import com.example.technologiesieciowe1.services.LoanService;
import com.example.technologiesieciowe1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/loan")
public class LoanController {

    private final BookService bookService;
    private final UserService userService;
    private final LoanService loanService;

    @Autowired
    public LoanController(BookService bookService,
                          UserService userService, LoanService loanService){
        this.userService = userService;
        this.bookService = bookService;
        this.loanService = loanService;
    }

    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public @ResponseBody Loan addLoan(@RequestBody Loan loan){return loanService.addLoan(loan);
    }
    @GetMapping("/getAll")
    public @ResponseBody Iterable<Loan> getAllLoans(){return loanService.getAllLoans();
    }

    @GetMapping("/{id}")
    public Loan getLoanById(@PathVariable Long id){
        Loan loan = loanService.getLoanById(id);
        if (loan == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan with id " + id + " not found");
        }
        return loan;
    }
    @PostMapping("/borrow")
    public ResponseEntity<Loan> borrowBook(@RequestParam Long userId, @RequestParam Long bookId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Book book = bookService.getBookById(bookId);
        Loan loan = loanService.borrowBook(user, book);
        return ResponseEntity.ok(loan);
    }
    @PutMapping("/return/{loanId}")
    public ResponseEntity<String> returnBook(@PathVariable Long loanId) {
        try {
            loanService.returnBook(loanId);
            return ResponseEntity.ok("Book returned successfully.");
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Loan not found.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PutMapping("/approve/{loanId}")
    public ResponseEntity<Void> approveLoan(@PathVariable Long loanId) {
        loanService.approveLoan(loanId);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/confirm-return/{loanId}")
    public ResponseEntity<Void> confirmReturn(@PathVariable Long loanId) {
        loanService.confirmReturn(loanId);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/delayed-returns")
    public ResponseEntity<List<Loan>> getDelayedReturns() {
        List<Loan> delayedReturns = loanService.getDelayedReturns();
        return new ResponseEntity<>(delayedReturns, HttpStatus.OK);
    }
}
