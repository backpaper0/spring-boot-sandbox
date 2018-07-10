package com.example.multitm;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.multitm.component.Bar;
import com.example.multitm.component.Foo;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MultiTransactionManagerApplication.class)
public class MultiTransactionManagerTest {

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
