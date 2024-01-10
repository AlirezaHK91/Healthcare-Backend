package com.example.healthAppStarter.Services;

import com.example.healthAppStarter.models.Booking;
import com.example.healthAppStarter.models.Schedule;
import com.example.healthAppStarter.repository.BookingRepository;
import com.example.healthAppStarter.repository.ScheduleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Transactional
class BookingServiceTest {

    @Mock
    BookingRepository bookingRepository;
    @Mock
    ScheduleRepository scheduleRepository;
    @Mock
    BookingService bookingService;
    @Mock
    ScheduleService scheduleService;

    @BeforeEach
    void setUp() {
        Booking booking = new Booking();
        Schedule schedule = new Schedule();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createBooking() {
    }

    @Test
    void updateIsDone() {
    }

    @Test
    void getBookingById() {
    }

    @Test
    void updateBooking() {
    }

    @Test
    void deleteBooking() {
    }

    @Test
    void getAllBooking() {
    }
}