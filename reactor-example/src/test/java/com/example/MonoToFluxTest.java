package com.example;

import org.junit.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class MonoToFluxTest {

    @Test
    public void test() throws Exception {
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
