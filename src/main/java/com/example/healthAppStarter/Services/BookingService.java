package com.example.healthAppStarter.Services;

import com.example.healthAppStarter.models.Booking;
import com.example.healthAppStarter.models.Schedule;
import com.example.healthAppStarter.repository.BookingRepository;
import com.example.healthAppStarter.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    ScheduleRepository scheduleRepository;

    public String createBooking(LocalDate date, Time time) {
       List<Schedule> schedules = scheduleRepository.findByDateAndTime(date, time);
       if (schedules != null && schedules.get(0).isAvailable()) {
           Booking booking = new Booking();
           booking.setSchedule(schedules.get(0));
           bookingRepository.save(booking);
           return "Booking confirmed from service";
       } else {
           return "Slot is not available from service";
       }
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id).get();
    }

    public Booking updateBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }
}
