package com.rest.api.boonyarisRestApi.service;

import com.rest.api.boonyarisRestApi.entity.Account;
import com.rest.api.boonyarisRestApi.model.request.AccountLoginRequest;
import com.rest.api.boonyarisRestApi.model.request.AccountRequest;
import com.rest.api.boonyarisRestApi.model.response.Response;
import com.rest.api.boonyarisRestApi.model.response.ResponseAccount;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

public interface AccountService {
    Response<List<Account>> inquiryAllAccount();

    Response<ResponseAccount> inquiryAccountById(Integer id);

    Response<T> deleteAccountById(Integer id);

    Response<Account> createAccount(AccountRequest accountRequest);

    Response<String> login(AccountLoginRequest accountLoginRequest);
}
