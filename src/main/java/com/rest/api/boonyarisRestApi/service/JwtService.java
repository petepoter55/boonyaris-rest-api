package com.rest.api.boonyarisRestApi.service;

import com.rest.api.boonyarisRestApi.entity.Account;
import com.rest.api.boonyarisRestApi.environment.Constant;
import com.rest.api.boonyarisRestApi.exception.ResponseException;
import com.rest.api.boonyarisRestApi.repository.AccountRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
public class JwtService {
    private static final Logger logger = LogManager.getLogger(JwtService.class);

    @Value("${jwt.secretKey}")
    private String secretKey;

    private final AccountRepository accountRepository;

    @Autowired
    public JwtService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    public String generateToken(Integer accountId) {
        logger.info("==== start Generate Token ====");
        logger.info("generate token account id : {}", accountId);

        Calendar currentDate = Calendar.getInstance();
        Date date = currentDate.getTime();
        currentDate.add(Calendar.MINUTE, 1);
        logger.info("generate token expire time : {}", currentDate);

        SecretKey key = Keys.hmacShaKeyFor(this.secretKey.getBytes(StandardCharsets.UTF_8));
        logger.info("==== done generate token ====");
        return Jwts.builder()
                .claim("accountId", accountId)
                .setIssuedAt(date)
                .setExpiration(currentDate.getTime())
                .signWith(key, SignatureAlgorithm.HS256).compact();
    }

    public void checkAccessToken(String token) {
        logger.info("==== start CheckAccess Token ====");
        try {
            SecretKey key = Keys.hmacShaKeyFor(this.secretKey.getBytes(StandardCharsets.UTF_8));
            Jws<Claims> jws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);

            Optional<Account> account = accountRepository.findById(Integer.parseInt(jws.getBody().get("accountId").toString()));
            if (!account.isPresent()) {
                throw new ResponseException(Constant.CODE_RESPONSE_INVALID_AUTH, Constant.MESSAGE_ERROR_TOKEN_INVALID);
            }
            logger.info("access token account id : {}", account.get().getId());
            logger.info("access token IssuedAt : {}", jws.getBody().getIssuedAt());
            logger.info("==== done CheckAccess Token ====");
        } catch (ExpiredJwtException e) {
            logger.error(e.getMessage(), e);
            throw new ResponseException(Constant.CODE_RESPONSE_TOKEN_EXPIRE, Constant.MESSAGE_EXPIRE);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ResponseException(Constant.CODE_RESPONSE_INVALID_AUTH, Constant.MESSAGE_ERROR_TOKEN_INVALID);
        }
    }
}
