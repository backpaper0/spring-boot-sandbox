package com.example;

import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.ipc.netty.http.server.HttpServer;

public class FunctionalEndpointsExample {

    public static void main(final String[] args) throws Exception {

        final RouterFunction<ServerResponse> routerFunction = HelloHandlers.routerFunction()
                .and(PersonHandlers.routerFunction());

        final HttpHandler handler = RouterFunctions.toHttpHandler(routerFunction);
        final ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(handler);
        HttpServer.create("localhost", 8080).newHandler(adapter).block();

        Thread.sleep(Long.MAX_VALUE);
    }
}
