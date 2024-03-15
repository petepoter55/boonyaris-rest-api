package com.rest.api.boonyarisRestApi.model.request;

import lombok.Data;

import java.util.List;

@Data
public class EmailRequest {
    private List<String> recipients;
    private List<String> ccList;
    private List<String> bccList;
    private String subject;
    private String body;
    private Boolean isHtml;
    private String attachmentPath;
}
