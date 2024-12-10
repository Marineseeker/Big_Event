package com.example.demo.service.impl;

import com.example.demo.mapper.UserMapper;
import com.example.demo.pojo.User;
import com.example.demo.service.UserService;
import com.example.demo.utils.Md5Util;
import com.example.demo.utils.UserUtil;

import java.time.LocalDateTime;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JavaMailSenderImpl mailSender;

    @Override
    public User findByUsername(String username) {
        User u = userMapper.findByUsername(username);
        return u;
    }

    @Override
    public User findByEmail(String email) {
        User u = userMapper.findByEmail(email);
        return u;
    }

    @Override
    public void register(String username, String password) {
        // Encrypt passwords
        String md5String = Md5Util.getMD5String(password);
        userMapper.add(username, md5String);
    }

    @Override
    public void update(User user){
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);
    }
    
    @Override
    public void updateAvatar(String avatarUrl){
        int userId = UserUtil.getUserId();
        userMapper.updateAvatar(avatarUrl, userId);
    }

    @Override
    public void updatePwd(String newPwd) {
        newPwd = Md5Util.getMD5String(newPwd);
        int userId = UserUtil.getUserId();
        userMapper.updatePwd(newPwd, userId);
    }

    @Override
    public void forgetPwd(String emailAddr, String newPwd){
        newPwd = Md5Util.getMD5String(newPwd);
        userMapper.forgetPwd(emailAddr, newPwd);
    }
    @Override
    public void sendVerifyCode(String emailAddr, String verifyCode) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("2716647396@qq.com");
            helper.setTo(emailAddr);
            helper.setSubject("Your Verification Code");
            helper.setText("Your verification code is: " + verifyCode, true);
            mailSender.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
    }
}
