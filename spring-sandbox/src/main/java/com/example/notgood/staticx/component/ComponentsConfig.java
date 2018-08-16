package com.example.notgood.staticx.component;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ComponentsConfig {

    @Bean
    public Hello hello() {
        return new Hello("********************%nHello, %s!%n********************%n");
    }
}
