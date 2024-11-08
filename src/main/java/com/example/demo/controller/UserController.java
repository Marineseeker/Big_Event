package com.example.demo.controller;

import com.example.demo.pojo.Result;
import com.example.demo.pojo.User;
import com.example.demo.service.UserService;
import com.example.demo.utils.CommenJwt;
// import com.example.demo.utils.JwtUtil;
import com.example.demo.utils.Md5Util;
import com.example.demo.utils.ThreadLocalUtil;

import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private CommenJwt commenJwt;

    @PostMapping("/register")
    public Result<String> register(@Pattern(regexp = "\\S{3,10}$") String username,
                           @Pattern(regexp = "\\S{5,16}$") String password) {
        User u = userService.findByUsername(username);
        if (u == null) {
            userService.register(username, password);
            return Result.success();
        } else {
            //occupied
            return Result.error("用户名已被占用");
        }
    }

    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "\\S{3,10}$") String username,
                                @Pattern(regexp = "\\S{5,16}$") String password){
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
            return Result.success(token2);
        }
        return Result.error("密码错误");
    }

    @GetMapping("/userInfo")
    public Result<User> userInfo(/*@RequestHeader("Authorization") String token*/){
        /*Map<String, Object> map = JwtUtil.parseToken(token);
        String username = (String)map.get("username");*/
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User user = userService.findByUsername(username);
        return Result.success(user);
    }
    
    @PutMapping("/update")
    public <T> Result<T> update(@RequestBody @Validated User user){
        userService.update(user);
        return Result.success();
    }
    
    @PatchMapping("/updateAvatar")
    public <T> Result<T> updateAvatar(@RequestParam String avatarUrl){
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    @PatchMapping("/updatePwd")
    public <T> Result<T> updatePwd(@RequestBody Map<String, String> params){
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
        return Result.success();
    }
}
