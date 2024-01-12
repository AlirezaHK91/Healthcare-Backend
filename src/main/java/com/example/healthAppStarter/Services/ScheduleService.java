package com.example.healthAppStarter.Services;

import com.example.healthAppStarter.models.Schedule;
import com.example.healthAppStarter.notification.Notification;
import com.example.healthAppStarter.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.List;

@Service
public class ScheduleService {
    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public Schedule createSchedule(Schedule schedule) {
        if (isTimeSlotAvailable(schedule)) {
            Schedule createdSchedule = scheduleRepository.save(schedule);

            // Mock WebSocket notification
            mockWebSocketNotification("New schedule created!");

            messagingTemplate.convertAndSend("/topic/schedule-notifications",
                    new Notification("New schedule created!"));

            return createdSchedule;
        } else {
            throw new IllegalArgumentException("There must be at least 1 hour between every schedules.");
        }
    }

    private boolean isTimeSlotAvailable(Schedule newSchedule) {

        List<Schedule> existingSchedules = scheduleRepository.findByDate(newSchedule.getDate());

        for (Schedule existingSchedule : existingSchedules) {
            if (!isHourGapValid(newSchedule.getTime(), existingSchedule.getTime())) {
                return false;
            }
        }

        return true;
    }

    private boolean isHourGapValid(Time newTime, Time existingTime) {
        long hourInMillis = 60 * 60 * 1000;
        long newTimeInMillis = newTime.getTime();
        long existingTimeInMillis = existingTime.getTime();

        return Math.abs(newTimeInMillis - existingTimeInMillis) >= hourInMillis;
    }

    public boolean checkIfAvailable (Schedule schedule) {
        return schedule.isAvailable();
    }
    //Mock notification method
    private void mockWebSocketNotification(String message) {
        System.out.println("WebSocket Notification: " + message);
    }

    public Schedule getScheduleById(Long id){
        return scheduleRepository.findById(id).get();
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public Schedule updateSchedule(Schedule schedule){
        return scheduleRepository.save(schedule);
    }

    public Schedule updateAvailability(Long id, boolean isAvailable) {
        Schedule existingSchedule = scheduleRepository.findById(id).orElse(null);

        if (existingSchedule != null) {
            existingSchedule.setAvailable(isAvailable);
            return scheduleRepository.save(existingSchedule);
        } else {
            throw new IllegalArgumentException("Schedule not found with id: " + id);
        }
    }

    public void deleteSchedule(Long id){
        scheduleRepository.deleteById(id);
    }
}
