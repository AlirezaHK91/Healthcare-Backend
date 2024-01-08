package com.example.healthAppStarter.controllers;

import com.example.healthAppStarter.Services.BookingService;
import com.example.healthAppStarter.models.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Collections;

@RestController
@RequestMapping(value = "/api/auth")
public class BookingController {

    @Autowired
    BookingService bookingService;

    @PostMapping("/booking")
    @PreAuthorize("hasRole ('ROLE_USER')")
    public String createBooking(@RequestBody Booking booking){
        LocalDate bookingDate = booking.getSchedule().getDate();
        Time bookingTime = booking.getSchedule().getTime();
        bookingService.createBooking(bookingDate, bookingTime);
        return "Booking confirmed from controller";
    }

    @GetMapping("/booking/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public Booking getBookingById(@PathVariable Long id){
        return bookingService.getBookingById(id);
    }

    @PutMapping("/booking/update")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public Booking updateBooking(@RequestBody Booking booking){
        return bookingService.updateBooking(booking);
    }

    @DeleteMapping("/booking/delete/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public String deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return "Booking has been deleted";
    }




}
