package com.example.webfluxexample;

import java.time.Duration;
import java.util.Objects;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonProperty;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public Mono<String> getHello() {
        // curl -v localhost:8080/hello
        return Mono.just("Hello, world!");
    }

    @GetMapping("/hello2")
    public Mono<Hello> getHello2() {
        // curl -v localhost:8080/hello2
        return Mono.just(new Hello("Hello, JSON!!"));
    }

    @GetMapping("/hello3")
    public Flux<Hello> getHello3() {
        // curl -v localhost:8080/hello3
        return Flux.range(1, 10)
                .map(i -> new Hello("Hello, Flux!!!" + i));
    }

    @GetMapping("/hello4")
    public Flux<Hello> getHello4() {
        // https://docs.spring.io/spring/docs/current/spring-framework-reference/web-reactive.html#webflux-codecs-streaming
        // JSON Streaming
        //   curl -v localhost:8080/hello4 -H "Accept: application/stream+json"
        // SSE
        //   curl -v localhost:8080/hello4 -H "Accept: text/event-stream"
        return Flux.range(1, 10)
                .map(i -> new Hello("Hello, HTTP Streaming!!!" + i))
                .delayElements(Duration.ofSeconds(1));
    }

    @GetMapping("/hello5")
    public Flux<Hello> getHello5(@RequestParam final String name) {
        // curl -v "localhost:8080/hello5?name=world"
        return Flux.just(new Hello("Hello, " + name + "!!!"));
    }

    public static final class Hello {

        private final String text;

        public Hello(@JsonProperty("text") final String text) {
            this.text = Objects.requireNonNull(text);
        }

        public String getText() {
            return text;
        }

        @Override
        public int hashCode() {
            return Objects.hash(text);
        }

        @Override
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            } else if (obj == null) {
                return false;
            } else if (obj.getClass() != getClass()) {
                return false;
            }
            final Hello other = getClass().cast(obj);
            return text.equals(other.text);
        }
    }
}
