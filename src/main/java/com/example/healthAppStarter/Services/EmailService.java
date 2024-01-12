package com.example.healthAppStarter.Services;

import com.example.healthAppStarter.models.Booking;
import com.example.healthAppStarter.models.User;
import com.example.healthAppStarter.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    UserRepository userRepository;
    public void sendBookingConfirmationEmail(Booking booking) {
        User user = userRepository.findById(booking.getUser().getId()).orElse(null);

        if (user != null) {
            String userEmail = user.getEmail();
            logger.info("Sending confirmation email to: {}", userEmail);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("Healthcare-AB");
            message.setTo(userEmail);
            message.setSubject("Booking Confirmation");

            String emailBody = String.format(
                    "Dear %s,\n\n" +
                            "Your booking has been confirmed. Here are the details:\n" +
                            "Description: %s\n" +
                            "Email: %s\n" +
                            "Thank you for using our service!",
                    user.getFullName(), booking.getDescription(), user.getEmail());
            message.setText(emailBody);
            javaMailSender.send(message);
            logger.info("Email sent successfully to: {}", userEmail);
        } else {
            logger.warn("User not found for booking with id: {}", booking.getId());
        }
    }
}