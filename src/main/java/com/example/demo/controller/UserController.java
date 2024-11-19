package com.example.demo.controller;

import com.example.demo.annotation.log;
import com.example.demo.dto.request.LoginRequest;
import com.example.demo.dto.request.RegisterRequest;
import com.example.demo.pojo.Result;
import com.example.demo.pojo.User;
import com.example.demo.service.UserService;
import com.example.demo.utils.CommenJwt;
// import com.example.demo.utils.JwtUtil;
import com.example.demo.utils.Md5Util;
import com.example.demo.utils.ThreadLocalUtil;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
//import org.springframework.web.bind.annotation.RequestHeader;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private CommenJwt commenJwt;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @log("用户注册")
    @PostMapping("/register")
    public Result<String> register(@Valid @RequestBody RegisterRequest registerRequest) {
        String username = registerRequest.getUsername();
        String password = registerRequest.getPassword();
        String rePassword = registerRequest.getRePassword();


        if (!password.equals(rePassword)) {
            return Result.error("两次密码不一致");
        }

        User u = userService.findByUsername(username);
        if (u == null) {
            userService.register(username, password);
            return Result.success();
        } else {
            //occupied
            return Result.error("用户名已被占用");
        }
    }

    @log("用户登录")
    @PostMapping("/login")
    public Result<String> login(@Valid @RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        User loginUser = userService.findByUsername(username);

        if(loginUser == null){
            return Result.error("用户名不存在");
        }
        
        if(loginUser.getPassword().equals(Md5Util.getMD5String(password))){
            //generate and return token
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", loginUser.getId());
            claims.put("username", loginUser.getUsername());
            // String token = JwtUtil.genToken(claims);
            String token2 = commenJwt.generateToken(username, claims);

            // 将token存入redis，设置过期时间为24小时
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set(token2, token2, 24, TimeUnit.HOURS);

            return Result.success(token2);
        }
        return Result.error("密码错误");
    }

    @log("登录用户详细信息")
    @GetMapping("/userInfo")
    public Result<User> userInfo(/*@RequestHeader("Authorization") String token*/){
        /*Map<String, Object> map = JwtUtil.parseToken(token);
        String username = (String)map.get("username");*/
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User user = userService.findByUsername(username);
        return Result.success(user);
    }

    @log("更新用户信息")
    @PutMapping("/update")
    public <T> Result<T> update(@RequestBody @Validated User user){
        userService.update(user);
        return Result.success();
    }

    @log("更新用户头像")
    @PatchMapping("/updateAvatar")
    public <T> Result<T> updateAvatar(@RequestParam String avatarUrl){
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    @PatchMapping("/updatePwd")
    public <T> Result<T> updatePwd(@RequestBody Map<String, String> params, @RequestHeader("Authorization") String token){
        String oldPwd = params.get("oldPwd");
        String newPwd = params.get("newPwd");
        String rePwd = params.get("rePwd");

        if(! StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)){
            return Result.error("密码不能为空");
        }

        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User user = userService.findByUsername(username);

        if(!user.getPassword().equals(Md5Util.getMD5String(oldPwd))){
            return Result.error("原密码错误");
        }

        if(!newPwd.equals(rePwd)){
            return Result.error("两次密码不一致");
        }

        if(newPwd.equals(oldPwd)){
            return Result.error("新密码不能与原密码相同");
        }

        userService.updatePwd(newPwd);

        // 密码更改之后, 将此时用户登陆的token删除
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.getOperations().delete(token);
        return Result.success();
    }
}
