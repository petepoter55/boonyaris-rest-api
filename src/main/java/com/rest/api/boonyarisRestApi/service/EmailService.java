package com.rest.api.boonyarisRestApi.service;

import com.rest.api.boonyarisRestApi.controller.AccountController;
import com.rest.api.boonyarisRestApi.model.request.EmailRequest;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.stream.Collectors;

@Service
public class EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender emailSender;

    @Autowired
    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public boolean sendSimpleMessage(EmailRequest emailRequest) {
        MimeMessage message = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);  // Set true for multipart messages
            helper.setFrom("boonyaris.p@aware.co.th");
            helper.setTo(String.join(",", emailRequest.getRecipients()));
            helper.setSubject(emailRequest.getSubject());
            helper.setText(emailRequest.getBody(), true);  // Set true for HTML content

        } catch (MessagingException e) {
            // Handle email sending exceptions gracefully (e.g., log the error)
            logger.error("Sending e-mail error: {}", e.getMessage());
        }

        boolean isSent = false;
        try {
            emailSender.send(message);
            isSent = true;
        } catch (Exception e) {
            logger.error("Sending e-mail error: {}", e.getMessage());
        }
        return isSent;
    }

}
