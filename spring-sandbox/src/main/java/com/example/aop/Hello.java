package com.example.aop;

import org.springframework.stereotype.Component;

@Component
public class Hello {

    @Asterisk
    public String say() {
        return "hello";
    }
}
