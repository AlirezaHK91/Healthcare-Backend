package com.example.healthAppStarter.Services;

import com.example.healthAppStarter.models.Review;
import com.example.healthAppStarter.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ReviewService {
    @Autowired
    ReviewRepository reviewRepository;

    public Review createReview (Review review) {
        review.setDone(true);
        return reviewRepository.save(review);
    }
    public void deleteReview(Long id) {
        try {
            reviewRepository.deleteById(id);
            System.out.println("Review with ID " + id + " deleted successfully.");
        } catch (Exception e) {
            System.out.println("Error deleting review with ID " + id + ": " + e.getMessage());
        }
    }
    public Review updateReview (Review review) {
        Review existingReview = reviewRepository.findById(review.getId()).orElse(null);
        if (existingReview != null) {
            existingReview.setUser(review.getUser());
            existingReview.setRating(review.getRating());
            existingReview.setComment(review.getComment());
            existingReview.setUpdatedAt(LocalDate.now());

            if (existingReview.getCreatedAt() == null) {
                existingReview.setCreatedAt(LocalDate.now());
            }
            return reviewRepository.save(existingReview);
        }
        return null;
    }
    public Review getReviewById (Long id) {
        return reviewRepository.findById(id).get();
    }
}
