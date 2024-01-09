package com.example.healthAppStarter.Services;

import com.example.healthAppStarter.models.Schedule;
import com.example.healthAppStarter.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {
    @Autowired
    ScheduleRepository scheduleRepository;

    public Schedule createSchedule(Schedule schedule) {
        if (isTimeSlotAvailable(schedule)) {
            return scheduleRepository.save(schedule);
        } else {
            throw new IllegalArgumentException("The specified date and time are already booked.");
        }
    }

    private boolean isTimeSlotAvailable(Schedule newSchedule) {
        List<Schedule> existingSchedules = scheduleRepository.findByDateAndTime(newSchedule.getDate(), newSchedule.getTime());
        return existingSchedules.isEmpty();
    }

    public boolean checkIfAvailable (Schedule schedule) {
        return schedule.isAvailable();
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
