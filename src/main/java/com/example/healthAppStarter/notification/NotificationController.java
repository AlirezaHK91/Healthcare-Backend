package com.example.healthAppStarter.notification;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationController {
    //Use prefix /app
    @MessageMapping("/send-notification")
    @SendTo("/topic/schedule-notifications")
    public Notification sendNotification(Notification notification) {

        return notification;
    }
}
