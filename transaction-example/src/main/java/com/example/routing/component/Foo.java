package com.example.routing.component;

import com.example.routing.LookupKey;
import com.example.routing.Use;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@Use(LookupKey.FOO)
public class Foo {

    public void foo() {}
}
