package com.example.healthAppStarter.Services;

import com.example.healthAppStarter.models.Schedule;
import com.example.healthAppStarter.repository.ScheduleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Time;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Transactional
class ScheduleServiceTest {

    @Mock
    private ScheduleRepository scheduleRepository;

    @InjectMocks
    private ScheduleService scheduleService;

    @Test
    void createSchedule() {
        Schedule schedule = createdSchedule();

        when(scheduleRepository.save(any())).thenReturn(schedule);

        Schedule createdSchedule = scheduleService.createSchedule(schedule);

        assertNotNull(createdSchedule);

        assertEquals(schedule.getDate(), createdSchedule.getDate());
        assertEquals(schedule.getTime(), createdSchedule.getTime());
        assertTrue(createdSchedule.isAvailable());

        verify(scheduleRepository, times(1)).save(any());
    }

    @Test
    void createScheduleValidateSchedule() {

        Schedule schedule1 = createdSchedule();

        when(scheduleRepository.save(any(Schedule.class)))
                .thenReturn(schedule1)
                .thenThrow(new IllegalArgumentException("The specified date and time are already booked."));

        scheduleService.createSchedule(schedule1);

        verify(scheduleRepository, times(1)).save(any(Schedule.class));

        assertThrows(IllegalArgumentException.class, () -> scheduleService.createSchedule(schedule1));

        verify(scheduleRepository, times(2)).save(any(Schedule.class));
    }

    @Test
    void updateSchedule() {
        Schedule schedule = createdSchedule();

        when(scheduleRepository.save(any())).thenReturn(schedule);

        scheduleService.createSchedule(schedule);

        schedule.setAvailable(false);

        Schedule updatedSchedule = scheduleService.updateSchedule(schedule);

        assertFalse(updatedSchedule.isAvailable());

        verify(scheduleRepository, times(2)).save(any());
    }

    @Test
    void deleteSchedule() {
        Schedule schedule = createdSchedule();
        schedule.setId(1L);

        scheduleService.deleteSchedule(schedule.getId());

        verify(scheduleRepository, times(1)).deleteById(1L);
    }

    private Schedule createdSchedule() {
        Schedule schedule = new Schedule();
        schedule.setDate(Date.valueOf("2024-01-02").toLocalDate());
        schedule.setTime(Time.valueOf("12:00:00"));
        schedule.setAvailable(true);
        return schedule;
    }
}
