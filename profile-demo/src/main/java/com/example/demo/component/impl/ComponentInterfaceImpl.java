package com.example.demo.component.impl;

import com.example.demo.component.ComponentInterface;
import com.example.demo.component.annotation.ComponentImpl;
import org.springframework.stereotype.Component;

@Component
@ComponentImpl
class ComponentInterfaceImpl implements ComponentInterface {

    @Override
    public String get() {
        return "impl";
    }
}
