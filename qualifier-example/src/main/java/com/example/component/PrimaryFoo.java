package com.example.component;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class PrimaryFoo extends AbstractFoo {

    public PrimaryFoo() {
        super("Primary foo");
    }
}
