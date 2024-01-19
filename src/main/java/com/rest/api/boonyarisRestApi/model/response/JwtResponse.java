package com.rest.api.boonyarisRestApi.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class JwtResponse {
    private String id;
    private Date expire;
    private Date issueDate;
}
