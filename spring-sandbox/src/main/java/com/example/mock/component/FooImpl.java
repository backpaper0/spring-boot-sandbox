package com.example.mock.component;

import com.example.mock.annotation.MyComponentImpl;
import org.springframework.stereotype.Component;

@Component
@MyComponentImpl(name = "com.example.mock.component.Foo", havingValue = "impl")
public class FooImpl implements Foo {

    @Override
    public String get() {
        return "impl";
    }
}
