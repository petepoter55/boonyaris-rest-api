package com.rest.api.boonyarisRestApi.service;

import com.rest.api.boonyarisRestApi.entity.Account;
import com.rest.api.boonyarisRestApi.environment.Constant;
import com.rest.api.boonyarisRestApi.exception.ResponseException;
import com.rest.api.boonyarisRestApi.model.request.AccountLoginRequest;
import com.rest.api.boonyarisRestApi.model.request.AccountRequest;
import com.rest.api.boonyarisRestApi.model.response.Response;
import com.rest.api.boonyarisRestApi.model.response.ResponseAccount;
import com.rest.api.boonyarisRestApi.repository.AccountRepository;
import com.rest.api.boonyarisRestApi.utils.DateUtils;
import com.rest.api.boonyarisRestApi.utils.UtilityTools;
import com.rest.api.boonyarisRestApi.validation.ValidatorAccount;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class AccountService {
    private static final Logger logger = LogManager.getLogger(AccountService.class);

    private final AccountRepository accountRepository;
    private final ValidatorAccount validatorAccount;
    private final UtilityTools utilityTools;
    private final DateUtils dateUtils;
    private final JwtService jwtService;
    private final ResponseMapperService responseMapperService;
    private final RequestMapperService requestMapperService;
    private final EmailService emailService;

    @Autowired
    public AccountService(AccountRepository accountRepository, ValidatorAccount validatorAccount, UtilityTools utilityTools, DateUtils dateUtils, JwtService jwtService, ResponseMapperService responseMapperService, RequestMapperService requestMapperService, EmailService emailService) {
        this.accountRepository = accountRepository;
        this.validatorAccount = validatorAccount;
        this.utilityTools = utilityTools;
        this.dateUtils = dateUtils;
        this.jwtService = jwtService;
        this.responseMapperService = responseMapperService;
        this.requestMapperService = requestMapperService;
        this.emailService = emailService;
    }

    public Response<List<Account>> inquiryAllAccount() {
        List<Account> accountList = accountRepository.findAll();
        if (accountList.isEmpty()) {
            throw new ResponseException(Constant.STATUS_CODE_FOUND, Constant.ERROR_UPDATE_DATA_NOT_FOUND);
        }
        return Response.success(Constant.STATUS_CODE_SUCCESS, Constant.MESSAGE_SUCCESS, accountList);
    }

    public Response<ResponseAccount> inquiryAccountById(Integer id) {
        Optional<Account> account;
        ResponseAccount responseAccount;
        try {
            account = accountRepository.findById(id);
            if (!account.isPresent()) {
                throw new ResponseException(Constant.STATUS_CODE_FOUND, Constant.ERROR_UPDATE_DATA_NOT_FOUND);
            }
            responseAccount = responseMapperService.mapInquiryAccount(account.get());
        } catch (ResponseException e) {
            return Response.fail(e.getExceptionCode(), e.getMessage());
        }
        return Response.success(Constant.STATUS_CODE_SUCCESS, Constant.MESSAGE_SUCCESS, responseAccount);
    }

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
            emailService.sendSimpleMessage(requestMapperService.mapEmailRequest(Constant.SUCCESS_REGISTER_ACCOUNT, getHtmlFile(), null, messages));
        } catch (ResponseException e) {
            logger.error(e.getDescription());
            return Response.fail(e.getExceptionCode(), e.getMessage());
        } catch (IOException | ParseException e) {
            logger.error(e.getMessage());
        }
        logger.info("==== done process create account =====");
        return Response.success(Constant.STATUS_CODE_SUCCESS, Constant.SUCCESS_LOGIN_ACCOUNT, account);
    }

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
            token = jwtService.generateToken(accountCheck.getId());
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
