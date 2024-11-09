package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static java.util.concurrent.TimeUnit.SECONDS;


@SpringBootTest
public class RedisTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Test
    public void TestSet(){
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.set("name", "zhangsan");
        operations.set("id", "1", 15, SECONDS);
    }
    @Test
    public void TestGet(){
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        String name = operations.get("name");
        System.out.println(name);
    }
}
