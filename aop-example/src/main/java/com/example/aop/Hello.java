package com.example.aop;

import com.example.Asterisk;
import org.springframework.stereotype.Component;

@Component
public class Hello {

    @Asterisk
    public String say() {
        return "Hello AOP";
    }
}
