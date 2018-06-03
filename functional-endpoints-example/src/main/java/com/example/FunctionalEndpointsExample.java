package com.example;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;
import reactor.ipc.netty.http.server.HttpServer;

public class FunctionalEndpointsExample {

    public static void main(final String[] args) throws Exception {

        final FunctionalEndpointsExample handlers = new FunctionalEndpointsExample();

        final RouterFunction<ServerResponse> routerFunction = RouterFunctions
                .route(GET("/hello"), handlers::hello);

        final HttpHandler handler = RouterFunctions.toHttpHandler(routerFunction);
        final ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(handler);
        HttpServer.create("localhost", 8080).newHandler(adapter).block();

        Thread.sleep(Long.MAX_VALUE);
    }

    Mono<ServerResponse> hello(final ServerRequest request) {
        return ServerResponse.ok().body(Mono.just("Hello, world!"), String.class);
    }
}
