package com.rest.api.boonyarisRestApi.service.impl;

import com.rest.api.boonyarisRestApi.entity.Account;
import com.rest.api.boonyarisRestApi.environment.Constant;
import com.rest.api.boonyarisRestApi.exception.ResponseException;
import com.rest.api.boonyarisRestApi.model.request.AccountLoginRequest;
import com.rest.api.boonyarisRestApi.model.request.AccountRequest;
import com.rest.api.boonyarisRestApi.model.response.Response;
import com.rest.api.boonyarisRestApi.model.response.ResponseAccount;
import com.rest.api.boonyarisRestApi.repository.AccountRepository;
import com.rest.api.boonyarisRestApi.service.*;
import com.rest.api.boonyarisRestApi.utils.DateUtils;
import com.rest.api.boonyarisRestApi.utils.RequestMapper;
import com.rest.api.boonyarisRestApi.utils.ResponseMapper;
import com.rest.api.boonyarisRestApi.utils.UtilityTools;
import com.rest.api.boonyarisRestApi.validation.ValidatorAccount;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    private static final Logger logger = LogManager.getLogger(AccountServiceImpl.class);

    private final AccountRepository accountRepository;
    private final ValidatorAccount validatorAccount;
    private final UtilityTools utilityTools;
    private final DateUtils dateUtils;
    private final JwtServiceImpl jwtServices;
    private final ResponseMapper responseMapper;
    private final RequestMapper requestMapper;
    private final EmailServiceImpl emailServices;
    private final RedisServiceImpl redisServices;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, ValidatorAccount validatorAccount, UtilityTools utilityTools, DateUtils dateUtils, JwtServiceImpl jwtServices, ResponseMapper responseMapper, RequestMapper requestMapper, EmailServiceImpl emailServices, RedisServiceImpl redisServices) {
        this.accountRepository = accountRepository;
        this.validatorAccount = validatorAccount;
        this.utilityTools = utilityTools;
        this.dateUtils = dateUtils;
        this.jwtServices = jwtServices;
        this.responseMapper = responseMapper;
        this.requestMapper = requestMapper;
        this.emailServices = emailServices;
        this.redisServices = redisServices;
    }

    @Override
    public Response<List<Account>> inquiryAllAccount() {
        List<Account> accountList = accountRepository.findAll();
        if (accountList.isEmpty()) {
            throw new ResponseException(Constant.STATUS_CODE_FOUND, Constant.ERROR_UPDATE_DATA_NOT_FOUND);
        }
        return Response.success(Constant.STATUS_CODE_SUCCESS, Constant.MESSAGE_SUCCESS, accountList);
    }

    @Override
    public Response<ResponseAccount> inquiryAccountById(Integer id) {
        Optional<Account> account;
        ResponseAccount responseAccount;
        try {
            account = accountRepository.findById(id);
            if (!account.isPresent()) {
                throw new ResponseException(Constant.STATUS_CODE_FOUND, Constant.ERROR_UPDATE_DATA_NOT_FOUND);
            }
            responseAccount = responseMapper.mapInquiryAccount(account.get());
            redisServices.getValueFromRedis(Constant.REDIS_KEY_INQUIRY_NAME, responseAccount.getUsername(), Constant.EXPIRED_REDIS_KEY_TOKEN_TIME_HOURS, Constant.EXPIRED_REDIS_KEY_TOKEN_TIME_HOURS_TYPE, Constant.REDIS_PROCESS_INQUIRY);
        } catch (ResponseException e) {
            return Response.fail(e.getExceptionCode(), e.getMessage());
        }
        return Response.success(Constant.STATUS_CODE_SUCCESS, Constant.MESSAGE_SUCCESS, responseAccount);
    }

    @Override
    public Response<T> deleteAccountById(Integer id) {
        logger.info("==== start delete account =====");
        logger.info("delete account id : {}", id);
        try {
            Optional<Account> account = accountRepository.findById(id);
            if (!account.isPresent()) {
                throw new ResponseException(Constant.STATUS_CODE_FOUND, Constant.ERROR_UPDATE_DATA_NOT_FOUND);
            }
            accountRepository.deleteById(id);
        } catch (ResponseException e) {
            throw new ResponseException(Constant.STATUS_CODE_FOUND, Constant.ERROR_UPDATE_DATA_NOT_FOUND);
        }
        logger.info("==== done delete account =====");
        return Response.success(Constant.STATUS_CODE_SUCCESS, Constant.MESSAGE_SUCCESS);
    }

    @Override
    public Response<Account> createAccount(AccountRequest accountRequest) {
        logger.info("==== start process create account =====");
        Account account = new Account();
        try {
            validatorAccount.validateRequestAccount(accountRequest);

            Account accountCheck = accountRepository.findByUsername(accountRequest.getUsername());
            if (accountCheck != null) {
                throw new ResponseException(Constant.STATUS_CODE_ERROR, Constant.ERROR_REGISTER_ALREADY_EXISTS);
            }

            account.setUsername(accountRequest.getUsername())
                    .setPassword(utilityTools.hashSha256(accountRequest.getPassword()))
                    .setFirstname(accountRequest.getFirstname())
                    .setLastname(accountRequest.getLastname())
                    .setEmail(accountRequest.getEmail())
                    .setRole(accountRequest.getRole())
                    .setCreateBy(accountRequest.getCreateBy())
                    .setDelFlag(accountRequest.getDelFlag())
                    .setCreateDateTime(dateUtils.getFormatsDateMilli());
            accountRepository.save(account);

            List<String> messages = Arrays.asList("boonyaris.p@aware.co.th");
            emailServices.sendSimpleMessage(requestMapper.mapEmailRequest(Constant.SUCCESS_REGISTER_ACCOUNT, getHtmlFile(), null, messages));
        } catch (ResponseException e) {
            logger.error(e.getDescription());
            return Response.fail(e.getExceptionCode(), e.getMessage());
        } catch (IOException | ParseException e) {
            logger.error(e.getMessage());
        }
        logger.info("==== done process create account =====");
        return Response.success(Constant.STATUS_CODE_SUCCESS, Constant.SUCCESS_LOGIN_ACCOUNT, account);
    }

    @Override
    public Response<String> login(AccountLoginRequest accountLoginRequest) {
        logger.info("==== start process login =====");
        logger.info("username : {}", accountLoginRequest.getUsername());
        String token = null;
        try {
            validatorAccount.validateUsername(accountLoginRequest.getUsername());

            Account accountCheck = accountRepository.findByUsername(accountLoginRequest.getUsername());
            if (accountCheck == null) {
                throw new ResponseException(Constant.STATUS_CODE_ERROR, Constant.ERROR_UPDATE_DATA_NOT_FOUND);
            }
            if (utilityTools.checkPassword(utilityTools.hashSha256(accountLoginRequest.getPassword()), accountCheck.getPassword())) {
                throw new ResponseException(Constant.STATUS_CODE_ERROR, Constant.ERROR_LOGIN_PASSWORD_INVALID);
            }
            token = jwtServices.generateToken(accountCheck.getId());
        } catch (ResponseException e) {
            logger.error("login failed because : {}", e.getDescription());
            return Response.fail(e.getExceptionCode(), e.getMessage());
        }
        logger.info("==== done process login =====");
        return Response.success(Constant.STATUS_CODE_SUCCESS, Constant.MESSAGE_SUCCESS, token);
    }

    private String getHtmlFile() throws IOException {
        File file = new File("src/main/resources/templates/registerDone.txt");
        return FileUtils.readFileToString(file, StandardCharsets.UTF_8);
    }
}
