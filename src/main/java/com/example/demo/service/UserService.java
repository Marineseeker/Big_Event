package com.example.demo.service;

import com.example.demo.pojo.User;


public interface UserService {
    User findByUsername(String username);

    User findByEmail(String email);

    void register(String username, String password);

    void update(User user);

    void updateAvatar(String avatarUrl);

    void updatePwd(String newPwd);

    void forgetPwd(String emailAddr, String newPwd);

    void sendVerifyCode(String emailAddr, String verifyCode);
}
