package com.rest.api.boonyarisRestApi.utils;

import com.rest.api.boonyarisRestApi.model.request.EmailRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RequestMapper {

    public EmailRequest mapEmailRequest(String subject, String body, String attachmentPath, List<String> recipients) {
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setBody(body);
        emailRequest.setSubject(subject);
        emailRequest.setRecipients(recipients);
        return emailRequest;
    }

}
