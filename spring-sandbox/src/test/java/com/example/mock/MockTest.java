package com.example.mock;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.mock.service.FooService;

public class MockTest {

	@SpringBootTest(properties = "com.example.mock.component.Foo=impl")
	static class ImplTest {

		@Autowired
		FooService service;

		@Test
		void test() {
			assertEquals("impl", service.get());
		}
	}

	@SpringBootTest(properties = "com.example.mock.component.Foo=mock1")
	static class Mock1Test {

		@Autowired
		FooService service;

		@Test
		void test() {
			assertEquals("mock1", service.get());
		}
	}

	@SpringBootTest(properties = "com.example.mock.component.Foo=mock2")
	static class Mock2Test {

		@Autowired
		FooService service;

		@Test
		void test() {
			assertEquals("mock2", service.get());
		}
	}

	@SpringBootTest
	static class FallbackTest {

		@Autowired
		FooService service;

		@Test
		void test() {
			assertThrows(UnsupportedOperationException.class, () -> service.get());
		}
	}
}
