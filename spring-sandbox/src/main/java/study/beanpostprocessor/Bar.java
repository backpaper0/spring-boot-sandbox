package study.beanpostprocessor;

import java.util.Objects;

import org.springframework.stereotype.Component;

@Component
public class Bar {

    private final Foo foo;

    public Bar(final Foo foo) {
        this.foo = Objects.requireNonNull(foo);
    }

    public String get() {
        return foo.get();
    }
}
