package com.rest.api.boonyarisRestApi.controller;

import com.rest.api.boonyarisRestApi.entity.Account;
import com.rest.api.boonyarisRestApi.exception.ResponseException;
import com.rest.api.boonyarisRestApi.model.request.AccountRequest;
import com.rest.api.boonyarisRestApi.model.response.Response;
import com.rest.api.boonyarisRestApi.service.AccountService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user-profile/accounts")
public class AccountController {
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @ApiOperation(value = "inquiry All Account", nickname = "inquiryAllAccount", notes = "Inquiry All Account from database")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 409, message = "Business Error"),
            @ApiResponse(code = 500, message = "Internal server error occurred"),
            @ApiResponse(code = 503, message = "Service Unavailable")})
    @GetMapping(value = "/inquiryAll")
    @ResponseStatus(HttpStatus.OK)
    public List<Account> inquiryAccount(
            HttpServletRequest request
    ) {
        logger.info("Path = {} method = {} INITIATED...",request.getRequestURI(),request.getMethod());
        return accountService.inquiryAllAccount();
    }

    @ApiOperation(value = "inquiry Account By Id", nickname = "inquiryAccountById", notes = "Inquiry Account By Id from database")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 409, message = "Business Error"),
            @ApiResponse(code = 500, message = "Internal server error occurred"),
            @ApiResponse(code = 503, message = "Service Unavailable")})
    @GetMapping(value = "/inquiryBy/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response inquiryAccount(
            @PathVariable(value = "id") Integer id,
            HttpServletRequest request
    ) throws ResponseException {
        logger.info("Path ={}" + request.getRequestURI() + ", method ={} " + request.getMethod() + " INITIATED...");
        return accountService.inquiryAccountById(id);
    }

    @ApiOperation(value = "Delete Account By Id", nickname = "deleteAccountById", notes = "Delete Account By Id from database")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 409, message = "Business Error"),
            @ApiResponse(code = 500, message = "Internal server error occurred"),
            @ApiResponse(code = 503, message = "Service Unavailable")})
    @DeleteMapping(value = "/deleteBy/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAccount(
            @PathVariable(value = "id") Integer id,
            HttpServletRequest request
    ) {
        logger.info("Path ={}" + request.getRequestURI() + ", method ={} " + request.getMethod() + " INITIATED...");
        accountService.deleteAccountById(id);
    }

    @ApiOperation(value = "Delete Account By Id", nickname = "deleteAccountById", notes = "Delete Account By Id from database")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 409, message = "Business Error"),
            @ApiResponse(code = 500, message = "Internal server error occurred"),
            @ApiResponse(code = 503, message = "Service Unavailable")})
    @PostMapping(value = "/createAccount")
    @ResponseStatus(HttpStatus.CREATED)
    public void createAccount(
            @Valid @RequestBody(required = true) AccountRequest accountRequest,
            HttpServletRequest request
    ) {
        logger.info("Path ={}" + request.getRequestURI() + ", method ={} " + request.getMethod() + " INITIATED...");
        accountService.createAccount(accountRequest);
    }
}
