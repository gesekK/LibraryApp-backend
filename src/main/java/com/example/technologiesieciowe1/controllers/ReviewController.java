package com.example.technologiesieciowe1.controllers;

import com.example.technologiesieciowe1.entities.Review;
import com.example.technologiesieciowe1.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public Iterable<Review> getAllReviews() {
        return reviewService.getAllReviews();
    }

    @GetMapping("/book/{title}")
    public Iterable<Review> getReviewsForBook(@PathVariable String title) {
        return reviewService.getReviewsForBook(title);
    }

    @GetMapping("/user/{userId}")
    public Iterable<Review> getReviewsByUser(@PathVariable Long userId) {
        return reviewService.getReviewsByUser(userId);
    }

    @PostMapping("/addReview")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Review addReview(@RequestBody Review review) {
        return reviewService.addReview(review);
    }
}
