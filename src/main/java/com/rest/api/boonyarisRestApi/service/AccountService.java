package com.rest.api.boonyarisRestApi.service;

import com.rest.api.boonyarisRestApi.entity.Account;
import com.rest.api.boonyarisRestApi.environment.Constant;
import com.rest.api.boonyarisRestApi.exception.ResponseException;
import com.rest.api.boonyarisRestApi.model.request.AccountRequest;
import com.rest.api.boonyarisRestApi.model.response.Response;
import com.rest.api.boonyarisRestApi.repository.AccountRepository;
import com.rest.api.boonyarisRestApi.utils.DateUtils;
import com.rest.api.boonyarisRestApi.utils.UtilityTools;
import com.rest.api.boonyarisRestApi.validation.ValidatorAccount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
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

    @Autowired
    public AccountService(AccountRepository accountRepository, ValidatorAccount validatorAccount, UtilityTools utilityTools, DateUtils dateUtils) {
        this.accountRepository = accountRepository;
        this.validatorAccount = validatorAccount;
        this.utilityTools = utilityTools;
        this.dateUtils = dateUtils;
    }

    public List<Account> inquiryAllAccount() {
        List<Account> accountList = accountRepository.findAll();
        if (accountList.isEmpty()) {
            throw new ResponseException(Constant.STATUS_CODE_FOUND, Constant.ERROR_UPDATE_DATA_NOT_FOUND);
        }
        return accountList;
    }

    public Response inquiryAccountById(Integer id) {
        Optional<Account> account;
        try {
            account = accountRepository.findById(id);
            if (!account.isPresent()) {
                throw new ResponseException(Constant.STATUS_CODE_FOUND, Constant.ERROR_UPDATE_DATA_NOT_FOUND);
            }
        } catch (ResponseException e) {
            return Response.fail(e.getExceptionCode(), e.getMessage());
        }
        return Response.success(Constant.STATUS_CODE_SUCCESS, Constant.MESSAGE_SUCCESS, account.get());
    }

    public void deleteAccountById(Integer id) {
        if (inquiryAccountById(id) == null) {
            throw new ResponseException(Constant.STATUS_CODE_FOUND, Constant.ERROR_UPDATE_DATA_NOT_FOUND);
        } else {
            accountRepository.deleteById(id);
        }
    }

    public void createAccount(AccountRequest accountRequest) {
        Account account = new Account();
        validatorAccount.validateRequestAccount(accountRequest);
        try {
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

        } catch (ResponseException e) {
            logger.error(e.getDescription());
        } catch (ParseException e) {
            logger.error(e.getMessage());
        }
    }
}
