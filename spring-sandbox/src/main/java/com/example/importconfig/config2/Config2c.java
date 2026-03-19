package com.example.importconfig.config2;

import com.example.misc.Baz;
import org.springframework.context.annotation.Bean;

public class Config2c {

    private boolean called;

    @Bean
    public Baz bazC() {
        if (called) {
            throw new RuntimeException();
        }
        called = true;
        return new Baz("C");
    }
}
