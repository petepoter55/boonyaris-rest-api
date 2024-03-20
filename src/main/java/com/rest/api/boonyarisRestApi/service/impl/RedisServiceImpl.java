package com.rest.api.boonyarisRestApi.service.impl;

import com.rest.api.boonyarisRestApi.entity.Account;
import com.rest.api.boonyarisRestApi.environment.Constant;
import com.rest.api.boonyarisRestApi.exception.ResponseException;
import com.rest.api.boonyarisRestApi.repository.AccountRepository;
import com.rest.api.boonyarisRestApi.service.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements RedisService {
    private static final Logger logger = LogManager.getLogger(RedisServiceImpl.class);

    private final StringRedisTemplate redisTemplate;
    private final AccountRepository accountRepository;

    @Autowired
    public RedisServiceImpl(StringRedisTemplate redisTemplate, AccountRepository accountRepository) {
        this.redisTemplate = redisTemplate;
        this.accountRepository = accountRepository;
    }

    @Override
    public void getValueFromRedis(String keyName, String value, String expire, String typeTime, String process) {
        long expireTime = Long.parseLong(expire);
        try {
            logger.info("Query key: {}", keyName);
            String valueRedis = redisTemplate.opsForValue().get(keyName);
            logger.info("Get Redis value from =====> Redis server : {}", valueRedis);

            if (StringUtils.isEmpty(valueRedis)) {
                logger.warn("Redis key {} not found, refreshing token", keyName);
                if (process.equals(Constant.REDIS_PROCESS_INQUIRY)) {
                    valueRedis = refreshTokenInquiry(value);
                }
            }
            //Save Redis Token
            redisTemplate.opsForValue().set(keyName, valueRedis != null ? valueRedis : "", expireTime, TimeUnit.valueOf(typeTime));
        } catch (Exception ex) {
            logger.error(String.format(Constant.THROW_EXCEPTION, ex.getMessage()));
        }
    }

    private String refreshTokenInquiry(String username) {
        String usernames = "";
        try {
            Account account = accountRepository.findByUsername(username);
            if (account == null) {
                throw new ResponseException(Constant.STATUS_CODE_FOUND, Constant.ERROR_UPDATE_DATA_NOT_FOUND);
            }
            usernames = account.getUsername();
        } catch (Exception e) {
            logger.error(String.format(Constant.THROW_EXCEPTION, e.getMessage()));
        }
        return usernames;
    }
}
