package com.example.technologiesieciowe1.repositories;

import com.example.technologiesieciowe1.entities.Review;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long> {

    @Query("""
        SELECT u.fullName, r.rating, r.comment, r.reviewDate
        FROM Review r
        JOIN r.user u
        WHERE r.book.title = :title
        """)
    Iterable<Review> findByBook(String title);

    @Query("""
        SELECT b.title, b.author, r.rating, r.comment, r.reviewDate
        FROM Review r
        JOIN r.book b
        WHERE r.user.userId = :userId
        """)
    Iterable<Review> findByUser(Long userId);
}

