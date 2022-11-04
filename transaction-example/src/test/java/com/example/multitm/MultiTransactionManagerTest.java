package com.example.multitm;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.multitm.component.Bar;
import com.example.multitm.component.Foo;

@SpringBootTest(classes = MultiTransactionManagerApplication.class)
class MultiTransactionManagerTest {

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
