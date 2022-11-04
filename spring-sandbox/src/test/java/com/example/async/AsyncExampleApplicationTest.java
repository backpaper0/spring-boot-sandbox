package com.example.async;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AsyncExampleApplicationTest {

	@Autowired
	private AsyncMethods methods;

	@Test
	void method1() throws Exception {
		assertEquals("default", methods.method1().get());
	}

	@Test
	void method2() throws Exception {
		assertEquals("alternative executor", methods.method2().get());
	}
}
