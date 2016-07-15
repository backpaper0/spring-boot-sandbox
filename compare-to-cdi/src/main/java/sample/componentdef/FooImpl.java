package sample.componentdef;

import org.springframework.stereotype.Component;

@Component
public class FooImpl implements Foo {

    @Override
    public String get() {
        return getClass().toString();
    }
}
