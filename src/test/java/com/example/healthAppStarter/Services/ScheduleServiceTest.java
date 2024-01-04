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
        Schedule schedule = new Schedule();
        schedule.setDate(Date.valueOf("2024-1-2").toLocalDate());
        schedule.setTime(Time.valueOf("10:00:00"));
        schedule.setAvailable(true);

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
        // Arrange
        Schedule schedule1 = createSampleSchedule();

        // Mock the repository behavior
        when(scheduleRepository.save(any(Schedule.class)))
                .thenReturn(schedule1) // Stub the first call
                .thenThrow(new IllegalArgumentException("The specified date and time are already booked."));

        // Act and Assert the first time
        scheduleService.createSchedule(schedule1);

        // Verify that the save method is called exactly once with any Schedule argument
        verify(scheduleRepository, times(1)).save(any(Schedule.class));

        // Act and Assert the second time to verify the exception
        assertThrows(IllegalArgumentException.class, () -> scheduleService.createSchedule(schedule1));

        // Verify that the save method is called again after the second invocation
        verify(scheduleRepository, times(2)).save(any(Schedule.class));
    }




    private Schedule createSampleSchedule() {
        Schedule schedule = new Schedule();
        schedule.setDate(Date.valueOf("2024-01-02").toLocalDate());
        schedule.setTime(Time.valueOf("12:00:00"));
        schedule.setAvailable(true);
        return schedule;
    }


    @Test
    void updateSchedule() {
        Schedule schedule = new Schedule();
        schedule.setDate(Date.valueOf("2024-1-2").toLocalDate());
        schedule.setTime(Time.valueOf("14:00:00"));
        schedule.setAvailable(true);

        when(scheduleRepository.save(any())).thenReturn(schedule);

        scheduleService.createSchedule(schedule);

        schedule.setAvailable(false);

        Schedule updatedSchedule = scheduleService.updateSchedule(schedule);

        assertFalse(updatedSchedule.isAvailable());

        verify(scheduleRepository, times(2)).save(any());
    }

    @Test
    void deleteSchedule() {
        Schedule schedule = new Schedule();
        schedule.setId(1L);
        schedule.setDate(Date.valueOf("2024-1-2").toLocalDate());
        schedule.setTime(Time.valueOf("14:00:00"));
        schedule.setAvailable(true);

        scheduleService.deleteSchedule(schedule.getId());

        verify(scheduleRepository, times(1)).deleteById(1L);
    }
}
