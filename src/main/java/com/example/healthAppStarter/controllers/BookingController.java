package com.example.healthAppStarter.controllers;

import com.example.healthAppStarter.Services.BookingService;
import com.example.healthAppStarter.models.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/auth")
public class BookingController {

    @Autowired
    BookingService bookingService;

    @PostMapping("/booking")
    @PreAuthorize("hasRole ('ROLE_USER')")
    public Booking createBooking(@RequestBody Booking booking){
        return bookingService.createBooking(booking);
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
