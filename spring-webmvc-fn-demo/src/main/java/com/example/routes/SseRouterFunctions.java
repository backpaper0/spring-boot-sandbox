package com.example.routes;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.time.Duration;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import reactor.core.publisher.Flux;

@Configuration
public class SseRouterFunctions {

	@Bean
	public RouterFunction<ServerResponse> sseRouterFunction() {
		return RouterFunctions.route()
				.GET("/sse", this::sse)
				.build();
	}

	private ServerResponse sse(ServerRequest request) {
		return ServerResponse.sse(builder -> {
			Flux<Long> flux = Flux.interval(Duration.ofSeconds(1)).skip(1).take(5);
			flux.subscribe(new Subscriber<Long>() {

				@Override
				public void onSubscribe(Subscription s) {
					s.request(Long.MAX_VALUE);
				}

				@Override
				public void onNext(Long t) {
					try {
						builder.send(t);
					} catch (IOException e) {
						throw new UncheckedIOException(e);
					}
				}

				@Override
				public void onError(Throwable t) {
					builder.error(t);
				}

				@Override
				public void onComplete() {
					builder.complete();
				}
			});
		});
	}
}
