package study.beanpostprocessor;

import org.springframework.stereotype.Component;

@Component
public class Foo {

    public String get() {
        return "hello";
    }
}
