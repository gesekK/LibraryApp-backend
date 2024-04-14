package com.example.technologiesieciowe1.repositories;

import com.example.technologiesieciowe1.entities.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

    Iterable<Book> findByAuthor(String author);
    Iterable<Book> findByTitle(String title);
    @Override
    void deleteById(Long id);
    Book findByIsbn(String isbn);
    @Query("""
            SELECT b.title, b.author, b.availableCopies
            FROM Book b
            ORDER BY b.countOfLoans
            DESC LIMIT 10""")
    Iterable<Book> findTop10ByOrderByCountOfLoansDesc();
}

