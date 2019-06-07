package com.example;

import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public final class FunctionalEndpoints {

    RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route()
                .GET("/simple", this::simple)
                .GET("/sse", this::sse)
                .build()
                .filter(new AddHeaderFilter());
    }

    Mono<ServerResponse> simple(final ServerRequest request) {
        return ServerResponse.ok().body(BodyInserters.fromObject("hello world"));
    }

    Mono<ServerResponse> sse(final ServerRequest request) {
        return ServerResponse.ok().body(BodyInserters.fromServerSentEvents(
                Flux.range(1, 5).map(a -> ServerSentEvent.builder().data(a).build())));
    }
}
