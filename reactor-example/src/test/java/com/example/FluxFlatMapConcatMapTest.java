package com.example;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class FluxFlatMapConcatMapTest {

	@Test
	void flatMap() throws Exception {
		final Flux<String> f = Flux.just("a", "b");
		final Flux<String> g = f
				.flatMap(a -> Flux.interval(Duration.ofMillis(100)).map(b -> a + b).take(3));
		StepVerifier.withVirtualTime(() -> g)
				.expectSubscription()
				.thenAwait(Duration.ofMillis(600))
				.expectNext("a0", "b0", "a1", "b1", "a2", "b2")
				.verifyComplete();
	}

	@Test
	void concatMap() throws Exception {
		final Flux<String> f = Flux.just("a", "b");
		final Flux<String> g = f
				.concatMap(a -> Flux.interval(Duration.ofMillis(100)).map(b -> a + b).take(3));
		StepVerifier.withVirtualTime(() -> g)
				.expectSubscription()
				.thenAwait(Duration.ofMillis(600))
				.expectNext("a0", "a1", "a2", "b0", "b1", "b2")
				.verifyComplete();
	}
}
