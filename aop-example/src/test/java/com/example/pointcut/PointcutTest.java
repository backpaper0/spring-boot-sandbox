package com.example.pointcut;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = PointcutExampleApplication.class)
public class PointcutTest {

	@Autowired
	private Hello sut;

	@Test
	void test() {
		assertEquals("*Hello Pointcut*", sut.say());
	}
}
