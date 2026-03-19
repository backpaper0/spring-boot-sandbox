package com.example.component;

import com.example.component.aop.BazAop;
import org.springframework.stereotype.Component;

@Component
@BazAop
public class Baz {

    public String name() {
        return "baz";
    }
}
