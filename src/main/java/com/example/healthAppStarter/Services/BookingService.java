package com.example.healthAppStarter.Services;

import com.example.healthAppStarter.models.Booking;
import com.example.healthAppStarter.models.Schedule;
import com.example.healthAppStarter.repository.BookingRepository;
import com.example.healthAppStarter.repository.ScheduleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

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
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    EmailService emailService;

    private static final Logger logger = (Logger) LoggerFactory.getLogger(BookingService.class);

    public Booking createBooking(Booking booking) {
        Optional<Schedule> bookedSchedule = scheduleRepository.findById(booking.getSchedule().getId());

        if (bookedSchedule.isPresent()) {
            Schedule schedule = bookedSchedule.get();

            if (scheduleService.checkIfAvailable(bookedSchedule.get())) {
                Booking savedBooking = bookingRepository.save(booking);
                scheduleService.updateAvailability(schedule.getId(), false);

                emailService.sendBookingConfirmationEmail(savedBooking);

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
        Optional<Booking> bookingOptional = bookingRepository.findById(id);

        if (bookingOptional.isPresent()) {
            Booking booking = bookingOptional.get();
            Long scheduleId = booking.getSchedule().getId();

            bookingRepository.deleteById(id);

            Optional<Schedule> scheduleOptional = scheduleRepository.findById(scheduleId);
            if (scheduleOptional.isPresent()) {
                Schedule schedule = scheduleOptional.get();
                scheduleService.updateAvailability(schedule.getId(), true);
            } else {
                throw new IllegalArgumentException("Schedule not found with id: " + scheduleId);
            }
        } else {
            throw new IllegalArgumentException("Booking not found with id: " + id);
        }
    }

    public List<Booking> getAllBooking(Long id) {
        return bookingRepository.findByUserId(id);
    }
}
