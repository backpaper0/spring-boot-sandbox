package com.example.common.httpclient;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SystemNanoTimeProviderTest {

	SystemNanoTimeProvider sut;

	@BeforeEach
	void init() {
		sut = new SystemNanoTimeProvider();
	}

	@Test
	void provide() {
		long a = System.nanoTime();
		long provided = sut.provide();
		long b = System.nanoTime();
		assertTrue(a <= provided);
		assertTrue(provided <= b);
	}
}
