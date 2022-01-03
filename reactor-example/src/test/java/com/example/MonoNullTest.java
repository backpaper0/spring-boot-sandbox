package com.example;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class MonoNullTest {

	@Test
	void nullToEmpty() {
		StepVerifier.withVirtualTime(() -> {
			return Mono.just("foo").mapNotNull(a -> null);
		})
				.verifyComplete();
	}
}
