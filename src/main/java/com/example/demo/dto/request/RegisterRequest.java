package com.example.demo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    // Getters and Setters
    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = "\\S{3,10}$", message = "用户名长度为3到10个字符，不能包含空格")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "\\S{5,16}$", message = "密码长度为5到16个字符，不能包含空格")
    private String password;

    @NotBlank
    @Pattern(regexp = "\\S{5,16}$", message = "密码长度为5到16个字符，不能包含空格")
    private String rePassword;
}