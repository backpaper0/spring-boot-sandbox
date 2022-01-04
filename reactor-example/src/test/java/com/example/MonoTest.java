package com.example;

import java.util.function.Function;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class MonoTest {

	@Test
	void mono() {
		StepVerifier.create(Mono.just("foo"))
				.expectNext("foo")
				.verifyComplete();

		StepVerifier.create(Mono.empty())
				.verifyComplete();

		StepVerifier.create(Mono.error(new Throwable()))
				.verifyError();
	}

	@Test
	void thenReturn() {
		Function<Mono<String>, Mono<String>> f = mono -> mono.thenReturn("bar");

		StepVerifier.create(f.apply(Mono.just("foo")))
				.expectNext("bar")
				.verifyComplete();

		StepVerifier.create(f.apply(Mono.empty()))
				.expectNext("bar")
				.verifyComplete();

		StepVerifier.create(f.apply(Mono.error(new Throwable())))
				.verifyError();
	}

	@Test
	void thenMono() {
		Function<Mono<String>, Mono<String>> f = mono -> mono.then(Mono.just("bar"));

		StepVerifier.create(f.apply(Mono.just("foo")))
				.expectNext("bar")
				.verifyComplete();

		StepVerifier.create(f.apply(Mono.empty()))
				.expectNext("bar")
				.verifyComplete();

		StepVerifier.create(f.apply(Mono.error(new Throwable())))
				.verifyError();
	}

	@Test
	void then() {
		Function<Mono<String>, Mono<Void>> f = mono -> mono.then();

		StepVerifier.create(f.apply(Mono.just("foo")))
				.verifyComplete();

		StepVerifier.create(f.apply(Mono.empty()))
				.verifyComplete();

		StepVerifier.create(f.apply(Mono.error(new Throwable())))
				.verifyError();
	}

	@Test
	void onErrorReturn() {
		Function<Mono<String>, Mono<String>> f = mono -> mono.onErrorReturn("bar");

		StepVerifier.create(f.apply(Mono.just("foo")))
				.expectNext("foo")
				.verifyComplete();

		StepVerifier.create(f.apply(Mono.empty()))
				.verifyComplete();

		StepVerifier.create(f.apply(Mono.error(new Throwable())))
				.expectNext("bar")
				.verifyComplete();
	}

	@Test
	void onErrorResumeMonoEmpty() {
		Function<Mono<String>, Mono<String>> f = mono -> mono.onErrorResume(e -> Mono.empty());

		StepVerifier.create(f.apply(Mono.just("foo")))
				.expectNext("foo")
				.verifyComplete();

		StepVerifier.create(f.apply(Mono.empty()))
				.verifyComplete();

		StepVerifier.create(f.apply(Mono.error(new Throwable())))
				.verifyComplete();
	}
}
