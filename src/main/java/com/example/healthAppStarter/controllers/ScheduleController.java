package com.example.healthAppStarter.controllers;

import com.example.healthAppStarter.Services.ScheduleService;
import com.example.healthAppStarter.models.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/auth")
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;

    @PostMapping("/schedule")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Schedule createSchedule(@RequestBody Schedule schedule){
        return scheduleService.createSchedule(schedule);
    }

    @GetMapping("/schedule/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Schedule getScheduleById(@PathVariable Long id){
        return scheduleService.getScheduleById(id);
    }

    @PutMapping("/schedule/update/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Schedule updateSchedule( @RequestBody Schedule schedule){
        return scheduleService.updateSchedule(schedule);
    }

    @DeleteMapping("/schedule/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteSchedule(@PathVariable Long id){
        scheduleService.deleteSchedule(id);
        return "Schedule has been deleted";
    }
}
