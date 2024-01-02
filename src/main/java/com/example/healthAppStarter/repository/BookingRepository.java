package com.example.healthAppStarter.repository;

import com.example.healthAppStarter.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
