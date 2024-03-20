package com.rest.api.boonyarisRestApi.utils;

import com.rest.api.boonyarisRestApi.entity.Account;
import com.rest.api.boonyarisRestApi.model.response.ResponseAccount;
import com.rest.api.boonyarisRestApi.utils.UtilityTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResponseMapper {
    private final UtilityTools tools;

    @Autowired
    public ResponseMapper(UtilityTools tools) {
        this.tools = tools;
    }

    public ResponseAccount mapInquiryAccount(Account account) {
        ResponseAccount responseAccount = new ResponseAccount();

        responseAccount.setFirstname(account.getFirstname());
        responseAccount.setLastname(account.getLastname());
        responseAccount.setEmail(account.getEmail());
        responseAccount.setUsername(account.getUsername());
        responseAccount.setRole(account.getRole());
        responseAccount.setDelFlag(account.getDelFlag());
        responseAccount.setCreateDateTime(tools.generateDateTimeToThai(account.getCreateDateTime()));
        responseAccount.setUpdateDateTime(tools.generateDateTimeToThai(account.getUpdateDateTime()));

        return responseAccount;
    }
}
