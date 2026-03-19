package com.example.mock.service;

import com.example.mock.component.Foo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FooService {

    @Autowired
    private Foo foo;

    public String get() {
        return foo.get();
    }
}
