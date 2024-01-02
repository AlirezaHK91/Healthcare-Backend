package com.example.healthAppStarter.repository;

import com.example.healthAppStarter.models.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
