package com.example.component;

import org.springframework.stereotype.Component;

import com.example.annotation.Tertiary;

@Component
@Tertiary
public class TertiaryFoo extends AbstractFoo {

    public TertiaryFoo() {
        super("Tertiary foo");
    }
}
