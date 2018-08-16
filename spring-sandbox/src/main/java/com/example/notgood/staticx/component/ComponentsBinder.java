package com.example.notgood.staticx.component;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.example.notgood.staticx.factory.HelloFactory;

@Component
public class ComponentsBinder {

    private final Hello hello;

    public ComponentsBinder(final Hello hello) {
        this.hello = hello;
    }

    @EventListener
    public void bind(final ContextRefreshedEvent event) {
        HelloFactory.setHello(hello);
    }
}
