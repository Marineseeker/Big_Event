package com.example.demo;

import org.junit.jupiter.api.Test;


public class ThreadLocalTest {
    public static final ThreadLocal<String> threadLocal = new ThreadLocal<>();
    @Test
    public void test(){
        
        new Thread(()->{
            threadLocal.set("limou");
            System.out.println(Thread.currentThread().getName() + " " + threadLocal.get());
        }, "blue").start();


        new Thread(()->{
            threadLocal.set("zhangmou");
            System.out.println(Thread.currentThread().getName() + " " + threadLocal.get());
        }, "green").start();
    }
}
