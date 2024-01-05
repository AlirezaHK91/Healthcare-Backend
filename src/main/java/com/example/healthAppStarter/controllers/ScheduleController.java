package com.example.healthAppStarter.controllers;

import com.example.healthAppStarter.Services.ScheduleService;
import com.example.healthAppStarter.models.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping(value = "/api/auth")
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;

    @PostMapping("/schedule")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> createSchedule(@RequestBody Schedule schedule){
        try {
            Schedule createdSchedule = scheduleService.createSchedule(schedule);
            return new ResponseEntity<>(createdSchedule, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(Collections.singletonMap("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/schedule/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Schedule getScheduleById(@PathVariable Long id){
        return scheduleService.getScheduleById(id);
    }

    @PutMapping("/schedule/update")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Schedule updateSchedule( @RequestBody Schedule schedule){
        return scheduleService.updateSchedule(schedule);
    }

    @PutMapping("/schedule/update/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<?> updateSchedule(@PathVariable Long id, @RequestBody Schedule updatedSchedule) {
        try {
            Schedule updatedScheduleResult = scheduleService.updateAvailability(id, updatedSchedule.isAvailable());
            return new ResponseEntity<>(updatedScheduleResult, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(Collections.singletonMap("error", e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/schedule/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteSchedule(@PathVariable Long id){
        scheduleService.deleteSchedule(id);
        return "Schedule has been deleted";
    }
}
