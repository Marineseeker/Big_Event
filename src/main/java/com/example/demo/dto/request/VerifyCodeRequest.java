package com.example.demo.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class VerifyCodeRequest {
    @Email
    @NotEmpty
    private String EmailAddr;
    @NotEmpty
    private String Code;
    private String newPwd;//密码
}
