package com.example.webfluxexample;

import java.util.Arrays;

import org.junit.Test;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.example.webfluxexample.HelloController.Hello;

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
        client.get().uri("/hello3").exchange()
                .expectBodyList(Hello.class)
                .isEqualTo(Arrays.asList(
                        new Hello("Hello, Flux!!!1"),
                        new Hello("Hello, Flux!!!2"),
                        new Hello("Hello, Flux!!!3"),
                        new Hello("Hello, Flux!!!4"),
                        new Hello("Hello, Flux!!!5"),
                        new Hello("Hello, Flux!!!6"),
                        new Hello("Hello, Flux!!!7"),
                        new Hello("Hello, Flux!!!8"),
                        new Hello("Hello, Flux!!!9"),
                        new Hello("Hello, Flux!!!10")));
    }
}
