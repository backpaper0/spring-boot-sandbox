package com.example.profile;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.profile.component.FoobarService;

@SpringBootTest
public class LocalTest {

	@Autowired
	FoobarService foobarService;

	@Test
	void test() {
		assertEquals("example:local", foobarService.get());
	}
}
