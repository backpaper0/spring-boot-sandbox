package com.example.component;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("secondary")
public class SecondaryFoo extends AbstractFoo {

    public SecondaryFoo() {
        super("Secondary foo");
    }
}
