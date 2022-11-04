package com.example;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class FluxTest {

	@Test
	void range() throws Exception {

		final Flux<Integer> actual1 = Flux.range(1, 10);

		StepVerifier.create(actual1)
				.expectNext(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
				.verifyComplete();

		final Flux<Integer> actual2 = Flux.range(100, 3);

		StepVerifier.create(actual2)
				.expectNext(100, 101, 102)
				.verifyComplete();
	}

	@Test
	void filter() throws Exception {

		final Flux<Integer> actual = Flux.range(1, 10)
				.filter(a -> a % 2 == 0);

		StepVerifier.create(actual)
				.expectNext(2, 4, 6, 8, 10)
				.verifyComplete();
	}

	@Test
	void map() throws Exception {

		final Flux<String> actual = Flux.just("hello", "world")
				//.map(String::toUpperCase)
				.map(a -> a.toUpperCase());

		StepVerifier.create(actual)
				.expectNext("HELLO", "WORLD")
				.verifyComplete();
	}

	@Test
	void reduce() throws Exception {

		//----------------------------------------------------------------
		// 1

		final Mono<String> actual1 = Flux.just("h", "e", "l", "l", "o")
				//.reduce(String::concat)
				.reduce((a, b) -> a.concat(b));

		//1. (h, e) -> he
		//2. (he, l) -> hel
		//3. (hel, l) -> hell
		//4. (hell, o) -> hello

		StepVerifier.create(actual1)
				.expectNext("hello")
				.verifyComplete();

		//----------------------------------------------------------------
		// 2

		final Mono<Integer> actual2 = Flux.range(1, 5)
				//.reduce(Integer::sum)
				.reduce((a, b) -> a + b);

		//1. (1, 2) -> 3
		//2. (3, 3) -> 6
		//3. (6, 4) -> 10
		//4. (10, 5) -> 15

		StepVerifier.create(actual2)
				.expectNext(15)
				.verifyComplete();

		//----------------------------------------------------------------
		// 3

		final Mono<Integer> actual3 = Flux.<Integer> empty()
				.reduce(Integer::sum);

		StepVerifier.create(actual3)
				.verifyComplete();

		//----------------------------------------------------------------
		// 4

		final Mono<Integer> actual4 = Flux.just(12345)
				.reduce(Integer::sum);

		StepVerifier.create(actual4)
				.expectNext(12345)
				.verifyComplete();

		//----------------------------------------------------------------
		// 5

		final Mono<String> actual5 = Flux.range(1, 5)
				.reduce("0", (a, b) -> a + b);

		StepVerifier.create(actual5)
				.expectNext("012345")
				.verifyComplete();
	}
}
