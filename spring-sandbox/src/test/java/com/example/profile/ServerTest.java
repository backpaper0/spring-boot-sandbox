package com.example.profile;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.profile.component.FoobarService;

@SpringBootTest
@ActiveProfiles("integration-test")
public class ServerTest {

	@Autowired
	FoobarService foobarService;

	@Test
	void test() {
		assertEquals("example:server", foobarService.get());
	}
}
