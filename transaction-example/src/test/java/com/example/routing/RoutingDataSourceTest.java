package com.example.routing;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.routing.component.Bar;
import com.example.routing.component.Foo;

@SpringBootTest(classes = RoutingDataSourceApplication.class)
class RoutingDataSourceTest {

    @Autowired
    private Foo foo;
    @Autowired
    private Bar bar;

    @Test
    void foo() {
        foo.foo();
    }

    @Test
    void bar() {
        bar.bar();
    }

    @Test
    void nest() {
        bar.nest();
    }
}
