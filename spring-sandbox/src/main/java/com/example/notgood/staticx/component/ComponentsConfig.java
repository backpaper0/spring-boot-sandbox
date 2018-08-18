package com.example.notgood.staticx.component;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import com.example.notgood.staticx.factory.HelloFactory;

@Configuration
public class ComponentsConfig {

    @Bean
    public Hello hello() {
        return new Hello("********************%nHello, %s!%n********************%n");
    }

    @EventListener
    public void bind(final ContextRefreshedEvent event) {
        HelloFactory.setHello(hello());
    }
}
