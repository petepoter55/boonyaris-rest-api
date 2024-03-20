package com.rest.api.boonyarisRestApi.service;

public interface JwtService {
    String generateToken(Integer accountId);

    void checkAccessToken(String token);
}
