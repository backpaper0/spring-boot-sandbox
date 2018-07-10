package com.example.routing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.routing.component.Bar;
import com.example.routing.component.Foo;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RoutingDataSourceApplication.class)
public class RoutingDataSourceTest {

    @Autowired
    private Foo foo;
    @Autowired
    private Bar bar;

    @Test
    public void foo() {
        foo.foo();
    }

    @Test
    public void bar() {
        bar.bar();
    }

    @Test
    public void nest() {
        bar.nest();
    }
}
