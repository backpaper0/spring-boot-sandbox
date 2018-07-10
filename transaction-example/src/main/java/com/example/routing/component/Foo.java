package com.example.routing.component;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.routing.LookupKey;
import com.example.routing.Use;

@Component
@Transactional
@Use(LookupKey.FOO)
public class Foo {

    public void foo() {
    }
}