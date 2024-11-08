package com.example.demo.pojo;



import lombok.Data;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Data
public class User {

    @NotNull
    private Integer id;//主键ID
    private String username;//用户名

    @JsonIgnore
    //返回用户信息时，不返回密码
    //在一个字段上使用 @JsonIgnore 注解时，Jackson 在将对象序列化为 JSON 时会忽略这个字段，不会将其包含在生成的 JSON 字符串中
    private String password;//密码
    
    @NotEmpty
    @Pattern(regexp = "\\S{2,10}$")
    private String nickname;//昵称

    @NotEmpty
    @Email
    private String email;//邮箱

    private String userPic;//用户头像地址
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//更新时间
}
