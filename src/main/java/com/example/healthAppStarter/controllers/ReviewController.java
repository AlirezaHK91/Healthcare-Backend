package com.example.healthAppStarter.controllers;

import com.example.healthAppStarter.Services.ReviewService;
import com.example.healthAppStarter.models.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/auth")
public class ReviewController {
    @Autowired
    ReviewService reviewService;

    @PostMapping("/review")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public Review createReview (@RequestBody Review review) {
        return reviewService.createReview(review);
    }
    @PutMapping("/review/update")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public Review updateReview (@RequestBody Review review) {
        return reviewService.updateReview(review);
    }
    @DeleteMapping("/review/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public String deleteReview (@PathVariable Long id) {
        reviewService.deleteReview(id);
        return "Review has been deleted";
    }
    @GetMapping("/review/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public Review getReviewById (@PathVariable Long id) {
        return reviewService.getReviewById(id);
    }

}
