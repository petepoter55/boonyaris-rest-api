package com.rest.api.boonyarisRestApi.service.impl;

import com.rest.api.boonyarisRestApi.model.request.EmailRequest;
import com.rest.api.boonyarisRestApi.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    private final JavaMailSender emailSender;

    @Autowired
    public EmailServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
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
