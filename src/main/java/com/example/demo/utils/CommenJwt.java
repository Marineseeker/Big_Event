package com.example.demo.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.security.Key;
import java.util.Date;
// import java.util.HashMap;
import java.util.Map;

@Component
public class CommenJwt {

    @Value("${jwt.secret}")
    private String secret;  // 从配置文件中读取密钥

    @Value("${jwt.expiration}")
    private long expiration;  // 令牌过期时间

    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());  // 使用密钥初始化 HMAC 加密密钥
    }

    // 生成 JWT 令牌
    public String generateToken(String username, Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)  // 自定义的附加信息
                .setSubject(username)  // 用户信息
                .setIssuedAt(new Date())  // 创建时间
                .setExpiration(new Date(System.currentTimeMillis() + expiration))  // 过期时间
                .signWith(key, SignatureAlgorithm.HS256)  // 签名算法
                .compact();
    }

    // 从 JWT 中解析用户信息
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    // 验证 JWT 是否有效
    public boolean validateToken(String token, String username) {
        return extractUsername(token).equals(username) && !isTokenExpired(token);
    }

    // 检查令牌是否过期
    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    // 从 JWT 中提取声明信息
    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public Claims getClaims(String token) {
        return extractClaims(token);
    }
}
