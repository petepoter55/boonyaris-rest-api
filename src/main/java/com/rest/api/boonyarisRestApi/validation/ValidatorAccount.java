package com.rest.api.boonyarisRestApi.validation;

import com.rest.api.boonyarisRestApi.environment.Constant;
import com.rest.api.boonyarisRestApi.exception.ResponseException;
import com.rest.api.boonyarisRestApi.model.request.AccountRequest;
import org.springframework.stereotype.Component;

@Component
public class ValidatorAccount {

    public void validateRequestAccount(AccountRequest accountRequest) {
        if (!accountRequest.getEmail().equals(accountRequest.getConfirmEmail()))
            throw new ResponseException(Constant.STATUS_CODE_ERROR, Constant.ERROR_ACCOUNT_REQUEST_EMAIL_INVALID);
    }

    public void validateUsername(String username) {
        if (username.isEmpty())
            throw new ResponseException(Constant.STATUS_CODE_ERROR, Constant.ERROR_LOGIN_USERNAME_IS_EMPTY);
    }
}
