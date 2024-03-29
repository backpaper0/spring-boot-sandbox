package com.example;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class StepVerifierTest {

	@Test
	void expectErrorMessage() throws Exception {

		final Flux<String> stream = Flux.<String> create(emitter -> {
			emitter.next("foo");
			emitter.next("bar");
			emitter.error(new Exception("baz"));
			emitter.next("qux"); //[DEBUG] (main) onNextDropped: qux
			emitter.complete();
		});

		StepVerifier.create(stream)
				.expectNext("foo")
				.expectNext("bar")
				.expectErrorMessage("baz")
				.verify();
	}
}
