package com.example;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.server.HandlerFilterFunction;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

public class AddHeaderFilter implements HandlerFilterFunction<ServerResponse, ServerResponse> {

    @Override
    public Mono<ServerResponse> filter(final ServerRequest request,
            final HandlerFunction<ServerResponse> next) {
        return next.handle(request).map(AddHeaderResponse::new);
    }

    static class AddHeaderResponse implements ServerResponse {

        final ServerResponse response;
        final HttpHeaders headers;

        public AddHeaderResponse(final ServerResponse response) {
            this.response = response;
            final HttpHeaders headers = HttpHeaders.writableHttpHeaders(response.headers());
            headers.add("X-Hoge", "FooBarBaz");
            this.headers = HttpHeaders.readOnlyHttpHeaders(headers);
        }

        @Override
        public HttpStatus statusCode() {
            return response.statusCode();
        }

        @Override
        public HttpHeaders headers() {
            return headers;
        }

        @Override
        public MultiValueMap<String, ResponseCookie> cookies() {
            return response.cookies();
        }

        @Override
        public Mono<Void> writeTo(final ServerWebExchange exchange, final Context context) {
            return response.writeTo(exchange, context);
        }
    }
}
