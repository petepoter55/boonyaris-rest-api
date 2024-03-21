package com.rest.api.boonyarisRestApi.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"username", "role", "firstname", "lastname", "email", "pagingOffset", "pageSize"})
public class AccountSearchRequest {
    @NotNull
    @Size(min = 1, max = 255)
    @JsonProperty("username")
    @ApiModelProperty(position = 1, required = true, dataType = "String", notes = "Username")
    private String username;

    @Size(max = 10)
    @JsonProperty("role")
    @ApiModelProperty(position = 4, required = true, dataType = "String", notes = "permission account")
    private String role;

    @Size(max = 50)
    @JsonProperty("firstname")
    @ApiModelProperty(position = 5, required = true, dataType = "String")
    private String firstname;

    @Size(max = 50)
    @JsonProperty("lastname")
    @ApiModelProperty(position = 6, required = true, dataType = "String")
    private String lastname;

    @Size(max = 50)
    @JsonProperty("email")
    @ApiModelProperty(position = 7, required = true, dataType = "String")
    private String email;

    @Size(max = 3)
    @JsonProperty("pagingOffset")
    @ApiModelProperty(position = 7, required = true, dataType = "Integer")
    private Integer pagingOffset = 0;

    @Size(max = 3)
    @JsonProperty("pageSize")
    @ApiModelProperty(position = 8, required = true, dataType = "Integer")
    private Integer pageSize = 10;

    @Override
    public String toString() {
        return "AccountSearchRequest{" +
                "username='" + username + '\'' +
                ", role='" + role + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", pagingOffset=" + pagingOffset +
                ", pageSize=" + pageSize +
                '}';
    }
}
