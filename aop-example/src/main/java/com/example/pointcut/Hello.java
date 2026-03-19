package com.example.pointcut;

import com.example.Asterisk;
import org.springframework.stereotype.Component;

@Component
@Asterisk
public class Hello {

    public String say() {
        return "Hello Pointcut";
    }
}
