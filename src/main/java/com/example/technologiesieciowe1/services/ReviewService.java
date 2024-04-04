package com.example.technologiesieciowe1.services;

import com.example.technologiesieciowe1.entities.Book;
import com.example.technologiesieciowe1.entities.Review;
import com.example.technologiesieciowe1.entities.User;
import com.example.technologiesieciowe1.repositories.BookRepository;
import com.example.technologiesieciowe1.repositories.ReviewRepository;
import com.example.technologiesieciowe1.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public Iterable<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Iterable<Review> getReviewsForBook(String title) {
        if (title != null) {
            Iterable<Review> reviews = reviewRepository.findByBook(title);
            if (reviews.iterator().hasNext()) {
                return reviews;
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No reviews found for the book with title: " + title);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Title cannot be null");
        }
    }

    public Iterable<Review> getReviewsByUser(Long userId) {
        if (userId != null) {
            Iterable<Review> reviews = reviewRepository.findByUser(userId);
            if (reviews.iterator().hasNext()) {
                return reviews;
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No reviews found for the user with ID: " + userId);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User ID cannot be null");
        }
    }

    public Review addReview(Review review) {
        if (review.getRating() < 0 || review.getRating() > 10) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Rating must be between 0 and 10");
        }

        Book book = bookRepository.findById(review.getBook().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found with ID: " + review.getBook().getId()));
        User user = userRepository.findById(review.getUser().getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with ID: " + review.getUser().getUserId()));

        review.setBook(book);
        review.setUser(user);
        review.setReviewDate(review.getReviewDate() != null ? review.getReviewDate() : new Date());

        return reviewRepository.save(review);
    }

}
