package com.example.aop;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = AopApplication.class)
public class AopTest {

	@Autowired
	private Hello sut;

	@Test
	void test() {
		assertEquals("*Hello AOP*", sut.say());
	}
}
