package com.example.demo.intercepters;

import com.example.demo.utils.CommenJwt;
// import com.example.demo.utils.JwtUtil;
import com.example.demo.utils.ThreadLocalUtil;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginIntercepter implements HandlerInterceptor {

    @Autowired
    private CommenJwt commenJwt;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从请求头中获取 token
        String token = request.getHeader("Authorization");
        try {
            // 验证该token是否能在redis中被查询到
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            if (operations.get(token) == null) {
                throw new RuntimeException();
            }

            //Map<String, Object> claims = JwtUtil.parseToken(token);
            Claims claims = commenJwt.getClaims(token);
            ThreadLocalUtil.set(claims);
            commenJwt.validateToken(token, (String) claims.get("username"));
            return true;
        } catch (Exception e) {
            response.setStatus(401);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadLocalUtil.remove();
    }
}
