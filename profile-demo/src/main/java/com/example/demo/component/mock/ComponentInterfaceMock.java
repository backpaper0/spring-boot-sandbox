package com.example.demo.component.mock;

import com.example.demo.component.ComponentInterface;
import com.example.demo.component.annotation.ComponentMock;
import org.springframework.stereotype.Component;

@Component
@ComponentMock
class ComponentInterfaceMock implements ComponentInterface {

    @Override
    public String get() {
        return "mock";
    }
}
