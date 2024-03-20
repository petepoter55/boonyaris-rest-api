package com.rest.api.boonyarisRestApi.service;

import com.rest.api.boonyarisRestApi.model.request.EmailRequest;

public interface EmailService {
    boolean sendSimpleMessage(EmailRequest emailRequest);
}
