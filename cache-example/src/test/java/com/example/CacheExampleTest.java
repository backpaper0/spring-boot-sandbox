package com.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CacheExampleTest {

	@Autowired
	CacheExample sut;

	@Test
	void test() {
		assertEquals("foo:1", sut.foo(1));
		assertEquals("foo:2", sut.foo(2));

		//keyがfoo(1)と被っているからキャッシュされているfoo:1が表示される
		assertEquals("foo:1", sut.bar(1));

		assertEquals("bar:3", sut.bar(3));
	}
}
