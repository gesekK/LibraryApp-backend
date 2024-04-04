package com.example.technologiesieciowe1.repositories;

import com.example.technologiesieciowe1.entities.Loan;
import com.example.technologiesieciowe1.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends CrudRepository<Loan, Long> {
    @Query("""
            SELECT  b.title, b.author, l.loanDate, l.returnDate, l.status
            FROM Loan l
            JOIN l.book b
            WHERE l.user.userId = :userId""")
    Iterable<Loan> findByUserId(Long userId);
    long countByUserAndStatus(User user, String borrowed);
}