package com.example.healthAppStarter.Services;

import com.example.healthAppStarter.models.Schedule;
import com.example.healthAppStarter.notification.Notification;
import com.example.healthAppStarter.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

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
            throw new IllegalArgumentException("The specified date and time are already booked.");
        }
    }

    private boolean isTimeSlotAvailable(Schedule newSchedule) {
        List<Schedule> existingSchedules = scheduleRepository.findByDateAndTime(newSchedule.getDate(), newSchedule.getTime());
        return existingSchedules.isEmpty();
    }
    //Mock notification method
    private void mockWebSocketNotification(String message) {
        System.out.println("WebSocket Notification: " + message);
    }

    public Schedule getScheduleById(Long id){
        return scheduleRepository.findById(id).get();
    }

    public Schedule updateSchedule(Schedule schedule){
        return scheduleRepository.save(schedule);
    }

    public void deleteSchedule(Long id){
        scheduleRepository.deleteById(id);
    }
}
