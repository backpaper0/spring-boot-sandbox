package com.example.mock.component;

import com.example.mock.annotation.MyComponentMock;
import org.springframework.stereotype.Component;

@Component
@MyComponentMock(name = "com.example.mock.component.Foo", havingValue = "mock2")
public class FooMock2 implements Foo {

    @Override
    public String get() {
        return "mock2";
    }
}
