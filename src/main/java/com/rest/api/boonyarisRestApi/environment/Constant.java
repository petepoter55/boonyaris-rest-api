package com.rest.api.boonyarisRestApi.environment;

import java.lang.reflect.Array;

public class Constant {
    //content type
    public static final String CONTENT_TYPE = "content-type";
    public static final String CONTENT_TYPE_JSON = "application/json";

    public static final String STATUS_CODE_SUCCESS = "200";
    public static final String STATUS_CODE_FAIL = "500";
    public static final String STATUS_CODE_ERROR = "400";
    public static final String STATUS_CODE_FOUND = "404";

    public static final Boolean STATUS_SUCCESS = true;
    public static final Boolean STATUS_FALSE = false;

    public static final String SUCCESS = "SUCCESS";

    //throw exception
    public static final String THROW_EXCEPTION = "Error : %s";

    //account request validator
    public static final String ERROR_ACCOUNT_REQUEST_EMAIL_INVALID = "Email or EmailConfirm invalid";
    public static final String ERROR_ACCOUNT_REQUEST_PASSWORD_INVALID = "Password or PasswordConfirm invalid";

    //register
    public static final String ERROR_REGISTER_CHECKDATA_DUPLICATE = "Data Register is Duplicate";
    public static final String ERROR_REGISTER_ALREADY_EXISTS = "Data Register is already exists";
    public static final String ERROR_UPDATE_DATA_NOT_FOUND = "Data Register is Not Found";
    public static final String ERROR_INQUIRY_DATA_NOT_FOUND = "Data Register is Not Found";
    public static final String ERROR_ACCOUNT_USERID_NULL = "User ID is Not Null";
    public static final String ERROR_ACCOUNT_USERID_SIZE = "User ID size must be between 1 and 5";
    public static final String ERROR_CREATE_IMAGE_ACCOUNT_DUPLICATE = "Data Image Account is Duplicate";
    public static final String ERROR_CREATE_IMAGE_ACCOUNT_NOT_FOUND = "Data Image Account is Not Found";
    public static final String ERROR_IMAGE_ACCOUNT_NOT_FOUND = "Data Image Account is Not Found";
    public static final String SUCCESS_REGISTER_ACCOUNT = "Register Account Success!";
    public static final String SUCCESS_UPDATE_ACCOUNT = "Update Account Success!";
    public static final String SUCCESS_DELETE_ACCOUNT = "Delete Account Success!";
    public static final String SUCCESS_INQUIRY_ACCOUNT = "Inquiry Account Success!";
    public static final String SUCCESS_CREATE_IMAGE_ACCOUNT = "Create Image Account Success!";
    public static final String SUCCESS_GET_IMAGE_ACCOUNT = "Get Image Account Success!";
    public static final String SUCCESS_UPDATE_IMAGE_ACCOUNT = "Update Image Account Success!";

    //login
    public static final String ERROR_LOGIN_DATA_NOT_FOUND = "Data Register is Not Found";
    public static final String ERROR_LOGIN_PASSWORD_INVALID = "Password or Username Invalid";
    public static final String SUCCESS_LOGIN_ACCOUNT = "Login Account Success!";
    public static final String ERROR_LOGIN_USERNAME_IS_EMPTY = "Username not be empty";

    //product
    public static final String SUCCESS_CREATE_PRODUCT = "Create Product Success!";
    public static final String SUCCESS_UPDATE_PRODUCT = "Update Product Success!";
    public static final String SUCCESS_DELETE_PRODUCT = "Delete Product Success!";
    public static final String SUCCESS_INQUIRY_PRODUCT = "Inquiry Product Success!";
    public static final String SUCCESS_CREATE_IMAGE_PRODUCT = "Create Image Product Success!";
    public static final String SUCCESS_GET_IMAGE_PRODUCT = "Get Image Product Success!";
    public static final String SUCCESS_UPDATE_IMAGE_PRODUCT = "Update Image Product Success!";
    public static final String SUCCESS_IMPORT_PRODUCT = "Import Product Success!";
    public static final String ERROR_CREATE_PRODUCT_DUPLICATE = "Data Product is Duplicate";
    public static final String ERROR_CREATE_IMAGE_PRODUCT_DUPLICATE = "Data Image Product is Duplicate";
    public static final String ERROR_PRODUCT_NOT_FOUND = "Data Product is Not Found";
    public static final String ERROR_IMAGE_PRODUCT_NOT_FOUND = "Data Image Product is Not Found";
    public static final String ERROR_PRODUCT_CODE_NULL = "Product Code is Not Null";
    public static final String ERROR_PRODUCT_CODE_SIZE = "Product Code size must be between 1 and 50";
    public static final String PRODUCT_STATUS_ACTIVE = "active";
    public static final String PRODUCT_STATUS_INACTIVE = "Inactive";
    public static final String ERROR_FILE_TYPE_INVALID = "Invalid File Type Should be Excel file.";
    public static final String ERROR_FILE_IMAGE_TYPE_INVALID = "Invalid File Type Should be {jpeg|png} file.";
    public static final String ERROR_FILE_IMAGE_NULL = "Invalid File is not null.";

    public static final String TYPE_FILE_EXCEL = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    public static final String TYPE_FILE_JPEG = "image/jpeg";
    public static final String TYPE_FILE_PNG = "image/png";
    public static final String[] TYPE_FILE_IMAGE = {TYPE_FILE_PNG,TYPE_FILE_JPEG};
    public static final String TYPE_FILE_XML = "application/xml";

    // account info
    public static final String ERROR_DATA_ACCOUNT_INFO_DUPLICATE = "Data Account Info is Duplicate";
    public static final String ERROR_DATA_ACCOUNT_INFO_NOT_FOUND = "Data Account Info is Not Found";

    //report
    public static final String ERROR_DATA_REPORT_NOT_FOUND = "Data Report is Not Found";
    public static final String ERROR_DATA_REPORT_DUPLICATE = "Data Report is Duplicate";
    public static final String ERROR_FILE_XML_TYPE_INVALID = "Invalid File Type Should be xml file";
    public static final String ERROR_FILE_XML_NOT_FOUND = "File request not Found";
    public static final String ERROR_REPORT_ID_REPORT_NULL = "report id is Not Null";
    public static final String ERROR_VERSION_REPORT_NULL = "version is Not Null";
    public static final String ERROR_START_DATE_REPORT_NULL = "startDate is Not Null";
    public static final String ERROR_END_DATE_REPORT_NULL = "endDate is Not Null";
    public static final String ERROR_NAME_REPORT_NULL = "name is Not Null";
    public static final String SUCCESS_CREATE_REPORT = "Create Report Success!";
    public static final String SUCCESS_UPDATE_REPORT = "Update Report Success!";
    public static final String SUCCESS_DELETE_REPORT = "Delete Report Success!";

    public static final String TYPE_REPORT_INSERT = "insert";
    public static final String TYPE_REPORT_UPDATE = "update";

    //log
    public static final String TYPE_REGISTER_SUCCESS = "R01";
    public static final String TYPE_UPDATE_SUCCESS = "R02";
    public static final String TYPE_LOGIN_SUCCESS = "R03";
    public static final String TYPE_CREATE_PRODUCT_SUCCESS = "R04";
    public static final String TYPE_REGISTER_FAILED = "F01";
    public static final String TYPE_UPDATE_FAILED = "F02";
    public static final String TYPE_LOGIN_FAILED = "F03";
    public static final String TYPE_CREATE_PRODUCT_FAILED = "F04";

    public static final String SUCCESS_INQUIRY_LOG = "Inquiry Log Success!";
    public static final String ERROR_INQUIRY_LOG_DATA_NOT_FOUND = "Data Log is Not Found";

    // line notification
    public static final String CONTENT_TYPE_REQUEST_LINE = "application/x-www-form-urlencoded";
    public static final String URL_NOTIFICATION = "https://notify-api.line.me/api/notify";

    //path interceptor
    public static final String PATH_SWAGGER_1 = "/v2/api-docs";
    public static final String PATH_SWAGGER_2 = "/swagger-resources/**";
    public static final String PATH_SWAGGER_3 = "/swagger-ui.html";
    public static final String PATH_SWAGGER_4 = "/webjars/**";
    public static final String PATH_CREATE_ACCOUNT = "/user-profile/accounts/createAccount";
//    public static final String PATH_INQUIRY_ACCOUNT_ALL = "/user-profile/accounts/inquiryAll";
    public static final String PATH_LOGIN_ACCOUNT = "/user-profile/accounts/loginAccount";
    public static final String[] PATH_EXCLUDE_INTERCEPTOR = new String[]{PATH_SWAGGER_1, PATH_SWAGGER_2, PATH_SWAGGER_3, PATH_SWAGGER_4, PATH_CREATE_ACCOUNT, PATH_LOGIN_ACCOUNT};

    // code
    public static final String CODE_RESPONSE_SUCCESS = "T001";
    public static final String CODE_RESPONSE_NOT_FOUND = "T002";
    public static final String CODE_RESPONSE_INVALID_PARAM = "T003";
    public static final String CODE_RESPONSE_INVALID_AUTH = "T004";
    public static final String CODE_RESPONSE_TOKEN_EXPIRE = "T005";
    public static final String CODE_RESPONSE_ERROR_OVER_LIMIT = "T006";
    public static final String CODE_RESPONSE_ERROR = "T999";

    // Message
    public static final String MESSAGE_SUCCESS = "success";
    public static final String MESSAGE_INVALID_PARAM = "Invalid Parameter";
    public static final String MESSAGE_OVER_LIMIT = "Limited Access";
    public static final String MESSAGE_ERROR_AUTH = "Authenticate Fail";
    public static final String MESSAGE_ERROR_PERMISSION = "No Permission";
    public static final String MESSAGE_ERROR_TOKEN_INVALID = "Invalid Authorization";
    public static final String MESSAGE_ERROR_PERIOD = "No Period";
    public static final String MESSAGE_EXPIRE = "Token Expired";
    public static final String MESSAGE_DATA_NOT_FOUND = "Data Not Found";
    public static final String MESSAGE_ERROR = "error";

    //redis
    public static final String EXPIRED_REDIS_KEY_TOKEN_TIME_HOURS_TYPE = "HOURS";
    public static final String EXPIRED_REDIS_KEY_TOKEN_TIME_HOURS = "1";
    public static final String REDIS_PROCESS_LOGIN =  "LOGIN";
    public static final String REDIS_KEY_INQUIRY_NAME =  "inquiry.username.key";
    public static final String REDIS_PROCESS_INQUIRY =  "INQUIRY";
    public static final String REDIS_KEY_TOKEN_NAME = "Jwt_token_interceptor";
}
