package com.example;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class StepVerifierWithVirtualTimeTest {

	@Test
	void delay() throws Exception {
		StepVerifier.withVirtualTime(() -> Mono.delay(Duration.ofSeconds(3)))
				.expectSubscription()
				.expectNoEvent(Duration.ofSeconds(3))
				.expectNext(0L)
				.verifyComplete();
	}

	@Test
	void interval() throws Exception {
		StepVerifier.withVirtualTime(() -> Flux.interval(Duration.ofSeconds(1)).take(3))
				.expectSubscription()
				.expectNoEvent(Duration.ofSeconds(1))
				.expectNext(0L)
				.expectNoEvent(Duration.ofSeconds(1))
				.expectNext(1L)
				.expectNoEvent(Duration.ofSeconds(1))
				.expectNext(2L)
				.verifyComplete();
	}
}
