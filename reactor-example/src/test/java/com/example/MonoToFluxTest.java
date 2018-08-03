package com.example;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class MonoToFluxTest {

    @Test
    void test() throws Exception {
        final Flux<String> flux = Mono.just("hello").flatMapMany(a -> Flux.just(a.split("")));
        StepVerifier.create(flux)
                .expectNext("h")
                .expectNext("e")
                .expectNext("l")
                .expectNext("l")
                .expectNext("o")
                .verifyComplete();
    }
}
