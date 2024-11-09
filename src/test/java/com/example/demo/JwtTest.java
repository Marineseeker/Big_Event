package com.example.demo;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {

    @Test
    public void testGenerator() {
        Map<String,Object> claims = new HashMap<>();
        claims.put("id",1);
        claims.put("username","limou");

        String token = JWT.create()
                .withClaim("user", "李海阳")
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .sign(Algorithm.HMAC256("Marine"));;
        System.out.println(token);
    }

//    @Test
//    public void testPass() {
//                        //头
//        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9." +
//                        //载荷
//                "eyJ1c2VyIjoi5p2O5rW36ZizIiwiZXhwIjoxNzI5NDA4NDA0fQ." +
//                        //签名
//                "HNRiHn3WWSVkNQqobmoF0RfTdU5R_I0D_hZwYfIrka8";
//        //生成验证器
//        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("Marine")).build();
//        //验证token
//        DecodedJWT decodedJWT = jwtVerifier.verify(token);
//        Map<String, Claim> claims = decodedJWT.getClaims();
//        System.out.println(claims.get("user").asString());
//        //token 密钥 过期时间 缺一不可
//    }
}
