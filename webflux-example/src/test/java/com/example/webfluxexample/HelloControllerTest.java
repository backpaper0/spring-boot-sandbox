package com.example.webfluxexample;

import org.junit.Test;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.example.webfluxexample.HelloController.Hello;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class HelloControllerTest {

    private final WebTestClient client = WebTestClient
            .bindToController(new HelloController())
            .build();

    @Test
    public void hello() {
        client.get().uri("/hello").exchange()
                .expectBody(String.class)
                .isEqualTo("Hello, world!");
    }

    @Test
    public void hello2() {
        client.get().uri("/hello2").exchange()
                .expectBody(Hello.class)
                .isEqualTo(new Hello("Hello, JSON!!"));
    }

    @Test
    public void hello3() {
        final Flux<Hello> responseBody = client.get().uri("/hello3").exchange()
                .returnResult(Hello.class).getResponseBody();

        StepVerifier.create(responseBody)
                .expectNext(new Hello("Hello, Flux!!!1"))
                .expectNext(new Hello("Hello, Flux!!!2"))
                .expectNext(new Hello("Hello, Flux!!!3"))
                .expectNext(new Hello("Hello, Flux!!!4"))
                .expectNext(new Hello("Hello, Flux!!!5"))
                .expectNext(new Hello("Hello, Flux!!!6"))
                .expectNext(new Hello("Hello, Flux!!!7"))
                .expectNext(new Hello("Hello, Flux!!!8"))
                .expectNext(new Hello("Hello, Flux!!!9"))
                .expectNext(new Hello("Hello, Flux!!!10"))
                .verifyComplete();
    }
}
