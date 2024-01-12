package com.example.healthAppStarter;

import com.example.healthAppStarter.Services.ScheduleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class AuthStarterApplicationTests {
    @Autowired
    private ScheduleService scheduleService;

	@Test
	void contextLoads() {
        assertNotNull(scheduleService);
	}

}
