package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.demo.foobar.Foobar;

@SpringBootTest
@ActiveProfiles("production")
public class ProductionProfileTest {

	@Autowired
	DemoApplication app;
	@Autowired
	Foobar foobar;

	@Test
	void test() {
		assertEquals("HELLO PRODUCTION!!!!!!!!!!!!!!!!!!!!!!!!!!", app.getHello());
	}

	@Test
	void testFoobar() {
		assertEquals("bar", foobar.get());
	}
}
