package com.rest.api.boonyarisRestApi.service;

public interface RedisService {
    void getValueFromRedis(String keyName, String value, String expire, String typeTime, String process);
}
