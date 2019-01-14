package com.example;

import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.RouterFunctions;

import reactor.netty.http.server.HttpServer;

public class App {

    public static void main(final String[] args) throws Exception {
        final var routerFunction = new FunctionalEndpoints().routerFunction();
        final var handler = RouterFunctions.toHttpHandler(routerFunction);
        final var adapter = new ReactorHttpHandlerAdapter(handler);
        HttpServer.create().host("localhost").port(8080).handle(adapter).bind().block();
        Thread.sleep(Long.MAX_VALUE);
    }
}
