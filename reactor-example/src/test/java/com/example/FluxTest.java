package com.example;

import org.junit.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FluxTest {

    @Test
    public void reduce() throws Exception {

        final Mono<String> mono = Flux.just("h", "e", "l", "l", "o")
                .reduce(String::concat);

        StepVerifier.create(mono)
                .expectNext("hello")
                .verifyComplete();
    }
}
