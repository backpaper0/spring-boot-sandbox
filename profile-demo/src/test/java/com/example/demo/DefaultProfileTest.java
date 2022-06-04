package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.foobar.Foobar;

@SpringBootTest
public class DefaultProfileTest {

	@Autowired
	DemoApplication app;
	@Autowired
	Foobar foobar;

	@Test
	void test() {
		assertEquals("HELLO DEFAULT!!!!!", app.getHello());
	}

	@Test
	void testFoobar() {
		assertEquals("foo", foobar.get());
	}
}
