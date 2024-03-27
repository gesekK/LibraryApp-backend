package com.example.technologiesieciowe1.controllers;

import com.example.technologiesieciowe1.entities.Book;
import com.example.technologiesieciowe1.google_books_api.BookDetail;
import com.example.technologiesieciowe1.exceptions.BookNotFoundException;
import com.example.technologiesieciowe1.services.BookDetailsService;
import com.example.technologiesieciowe1.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;
    private final BookDetailsService bookDetailsService;

    @Autowired
    public BookController(BookService bookService, BookDetailsService bookDetailsService){
        this.bookService = bookService;
        this.bookDetailsService = bookDetailsService;
    }

    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public @ResponseBody Book addBook(@RequestBody Book book){
        return bookService.addBook(book);
    }

    @GetMapping("/getAll")
    public @ResponseBody Iterable<Book> getAllBooks(){
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id){
        Book book = bookService.getBookById(id);
        if (book == null) {
            throw new BookNotFoundException("Book with id " + id + " not found");
        }
        return book;
    }
    @GetMapping("/author/{author}")
    public Iterable<Book> getBooksByAuthor(@PathVariable String author) {
        Iterable<Book> books = bookService.getBooksByAuthor(author);
        if (books == null) {
            throw new BookNotFoundException("No books found");
        }
        return books;
    }
    @GetMapping("/title/{title}")
    public Iterable<Book> getBooksByTitle(@PathVariable String title){
        Iterable<Book> books = bookService.getBooksByTitle(title);
        if (books == null) {
            throw new BookNotFoundException("No books found");
        }
        return books;
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBookById(@PathVariable Long id){
        bookService.deleteBookById(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Book> updateBookPartially(@PathVariable Long id, @RequestBody Map<String, Object> updates){
        Book book = bookService.partiallyUpdateBook(id, updates);
        return ResponseEntity.ok(book);
    }

    @PutMapping("/update/all/{id}")
    public ResponseEntity<Book> updateBookCompletely(@PathVariable Long id, @RequestBody Book updatedBook) {
        Book book = bookService.updateBook(id, updatedBook);
        return ResponseEntity.ok(book);
    }
    @GetMapping("/most-borrowed")
    public ResponseEntity<Iterable<Book>> getTop10MostBorrowedBooks() {
        Iterable<Book> top10Books = bookService.getTop10MostBorrowedBooks();
        return new ResponseEntity<>(top10Books, HttpStatus.OK);
    }
    @GetMapping("/details/{title}")
    public BookDetail getBookDetails(@PathVariable String title) {
        return bookDetailsService.getBookDetails(title);
    }
}
