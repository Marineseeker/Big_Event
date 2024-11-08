package com.example.demo.service.impl;

import com.example.demo.mapper.UserMapper;
import com.example.demo.pojo.User;
import com.example.demo.service.UserService;
import com.example.demo.utils.Md5Util;
import com.example.demo.utils.UserUtil;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUsername(String username) {
        User u = userMapper.findByUsername(username);
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
}
