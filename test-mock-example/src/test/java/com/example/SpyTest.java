package com.example;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

@SpringBootTest
public class SpyTest {

	@Autowired
	private MyService service;

	@SpyBean
	private Foobar spy;

	@Test
	void noSpy() {
		assertEquals("FooBar", service.foobar());
	}

	@Test
	void spyBar() {
		when(spy.bar()).thenReturn("Baz");
		assertEquals("FooBaz", service.foobar());

		verify(spy).foo();
		verify(spy).bar();
		verifyNoMoreInteractions(spy);
	}
}
