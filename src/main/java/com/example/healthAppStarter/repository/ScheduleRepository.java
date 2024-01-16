package com.example.healthAppStarter.repository;

import com.example.healthAppStarter.models.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByDateAndFormattedTime(LocalDate date, Time formattedTime);

    List<Schedule> findByDate(LocalDate date);

}
