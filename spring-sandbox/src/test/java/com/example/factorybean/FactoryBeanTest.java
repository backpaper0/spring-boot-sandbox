package com.example.factorybean;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
public class FactoryBeanTest {

	@Autowired
	Foo foo;
	@Autowired
	ApplicationContext ac;

	@Test
	void foo() throws Exception {
		System.out.println(foo);
		System.out.println(ac.getBean(Foo.class));
		System.out.println(ac.getBean(Foo.class));
		System.out.println(ac.getBean(FooFactory.class));
		assertNotNull(foo);
	}
}
