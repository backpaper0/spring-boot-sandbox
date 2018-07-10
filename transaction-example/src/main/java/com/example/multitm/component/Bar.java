package com.example.multitm.component;

import java.util.Objects;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional("barTransactionManager")
public class Bar {

    private final Foo foo;

    public Bar(final Foo foo) {
        this.foo = Objects.requireNonNull(foo);
    }

    public void bar() {
    }

    public void nest() {
        foo.foo();
    }
}