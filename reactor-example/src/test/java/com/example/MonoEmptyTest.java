package com.example;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class MonoEmptyTest {

	@Test
	void switchIfEmpty() throws Exception {
		StepVerifier.withVirtualTime(() -> {
			final Mono<String> m = Mono.empty();
			return m.switchIfEmpty(Mono.delay(Duration.ofSeconds(1)).map(x -> "hello"));
		})
				.expectSubscription()
				.expectNoEvent(Duration.ofSeconds(1))
				.expectNext("hello")
				.verifyComplete();
	}

	@Test
	void defaultIfEmpty() throws Exception {
		final Mono<String> m1 = Mono.empty();
		final Mono<String> m2 = m1.defaultIfEmpty("hello");
		StepVerifier.create(m2)
				.expectNext("hello")
				.verifyComplete();
	}
}
