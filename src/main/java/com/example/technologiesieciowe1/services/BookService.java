package com.example.technologiesieciowe1.services;

import com.example.technologiesieciowe1.entities.Book;
import com.example.technologiesieciowe1.exceptions.BookNotFoundException;
import com.example.technologiesieciowe1.repositories.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }
    public Book addBook(Book book){
        if (bookRepository.findByIsbn(book.getIsbn()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Book with ISBN " + book.getIsbn() + " already exists");
        }
        return bookRepository.save(book);
    }
    public Iterable<Book> getAllBooks(){
        return bookRepository.findAll();
    }
    public Book getBookById(Long id){
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));
    }
    public Iterable<Book> getBooksByAuthor(String author){
        return bookRepository.findByAuthor(author);
    }
    public Iterable<Book> getBooksByTitle(String title){
        return bookRepository.findByTitle(title);
    }
    public void deleteBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));
        bookRepository.delete(book);
    }
    public Book updateBook(Long id, Book updatedBook){
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book with id: " + id + " not found"));

        book.setIsbn(updatedBook.getIsbn());
        book.setTitle(updatedBook.getTitle());
        book.setAuthor(updatedBook.getAuthor());
        book.setPublisher(updatedBook.getPublisher());
        book.setPublishYear(updatedBook.getPublishYear());
        book.setAvailableCopies(updatedBook.getAvailableCopies());

        return bookRepository.save(book);
    }

    public Book partiallyUpdateBook(Long id, Map<String, Object> updates){
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));

        updates.forEach((key, value) -> {
            switch (key) {
                case "isbn" -> book.setIsbn((String) value);
                case "title" -> book.setTitle((String) value);
                case "author" -> book.setAuthor((String) value);
                case "publisher" -> book.setPublisher((String) value);
                case "publishYear" -> book.setPublishYear((Integer) value);
                case "availableCopies" -> book.setAvailableCopies((Integer) value);
            }
        });

        return bookRepository.save(book);
    }

    @Transactional
    public Iterable<Book> getTop10MostBorrowedBooks() {
        return bookRepository.findTop10ByOrderByCountOfLoansDesc();
    }

}
