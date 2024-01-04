package com.example.healthAppStarter.Services;

import com.example.healthAppStarter.models.Schedule;
import com.example.healthAppStarter.repository.ScheduleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Time;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class ScheduleServiceTest {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Test
    @Rollback(false)
    void createSchedule() {
        Schedule schedule = new Schedule();
        schedule.setDate(Date.valueOf("2024-1-2").toLocalDate());
        schedule.setTime(Time.valueOf("10:00:00"));
        schedule.setAvailable(true);

        Schedule createdSchedule = scheduleService.createSchedule(schedule);

        assertNotNull(createdSchedule.getId());
        assertEquals(schedule.getDate(), createdSchedule.getDate());
        assertEquals(schedule.getTime(), createdSchedule.getTime());
        assertTrue(createdSchedule.isAvailable());
    }

    @Test
    @Rollback(false)
    void createScheduleValidateSchedule() {
        Schedule schedule1 = new Schedule();
        schedule1.setDate(Date.valueOf("2024-1-2").toLocalDate());
        schedule1.setTime(Time.valueOf("12:00:00"));
        schedule1.setAvailable(true);
        scheduleService.createSchedule(schedule1);

        Schedule schedule2 = new Schedule();
        schedule2.setDate(Date.valueOf("2024-1-2").toLocalDate());
        schedule2.setTime(Time.valueOf("12:00:00"));
        schedule2.setAvailable(true);

        assertThrows(IllegalArgumentException.class, () -> scheduleService.createSchedule(schedule2));
    }

    @Test
    @Rollback(false)
    void updateSchedule() {
        Schedule schedule = new Schedule();
        schedule.setDate(Date.valueOf("2024-1-2").toLocalDate());
        schedule.setTime(Time.valueOf("14:00:00"));
        schedule.setAvailable(true);
        scheduleService.createSchedule(schedule);

        schedule.setAvailable(false);

        Schedule updatedSchedule = scheduleService.updateSchedule(schedule);

        assertFalse(updatedSchedule.isAvailable());
    }

    @Test
    @Rollback(false)
    void deleteSchedule() {
        Schedule schedule = new Schedule();
        schedule.setDate(Date.valueOf("2024-1-2").toLocalDate());
        schedule.setTime(Time.valueOf("16:00:00"));
        schedule.setAvailable(true);
        scheduleService.createSchedule(schedule);

        scheduleService.deleteSchedule(schedule.getId());

        assertTrue(scheduleRepository.findById(schedule.getId()).isEmpty());
    }
}