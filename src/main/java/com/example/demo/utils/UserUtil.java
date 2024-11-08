package com.example.demo.utils;

import java.util.Map;

public class UserUtil {

    public static int getUserId() {
        Map<String, Object> map = ThreadLocalUtil.get();
        return (int) map.get("id");
    }
    public static String getUsername() {
        Map<String, Object> map = ThreadLocalUtil.get();
        return (String) map.get("username");
    }
}