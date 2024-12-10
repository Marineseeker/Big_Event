package com.example.demo.utils;

import java.util.Random;

public class VerifyCodeUtil {
    public static String generateCode(int length){
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }
}
