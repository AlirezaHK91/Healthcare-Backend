package com.example.healthAppStarter.Services;

import com.example.healthAppStarter.models.Booking;
import com.example.healthAppStarter.models.Schedule;
import com.example.healthAppStarter.repository.BookingRepository;
import com.example.healthAppStarter.repository.ScheduleRepository;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    ScheduleService scheduleService;


    public Booking createBooking(Booking booking) {
        Optional<Schedule> bookedSchedule = scheduleRepository.findById(booking.getSchedule().getId());

        if (bookedSchedule.isPresent()) {
            Schedule schedule = bookedSchedule.get();

            if (scheduleService.checkIfAvailable(bookedSchedule.get()) ) {
                Booking savedBooking = bookingRepository.save(booking);
                scheduleService.updateAvailability(schedule.getId(), false);
                return savedBooking;
            }
        } else {
            throw new IllegalArgumentException("The specified schedule is already booked or unavailable.");
        }
        return booking;
    }

    public Booking updateIsDone(Long id, boolean isDone) {
        Booking booking = bookingRepository.findById(id).orElse(null);
        if (booking != null) {
            booking.setDone(isDone);
            return bookingRepository.save(booking);
        } else {
            throw new IllegalArgumentException("Schedule not found with id: " + id);
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

    public List<Booking> getAllBooking(Long id) {
        return bookingRepository.findByUserId(id);
    }
}
