package com.example.healthAppStarter.repository;

import com.example.healthAppStarter.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
