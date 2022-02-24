package com.example;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@RestController
@ConfigurationProperties(prefix = "example")
public class ExampleController {

	private static final Logger logger = LoggerFactory.getLogger(ExampleController.class);

	@Autowired
	private ExampleService service;
	@Autowired
	private WebClient web;
	private String url;

	@GetMapping("/foo1")
	public Object foo1(@RequestHeader HttpHeaders headers) {
		logger.info("foo1: {}", headers);
		return service.foo1();
	}

	@GetMapping("/foo2")
	public Object foo2() {
		return service.foo2();
	}

	@GetMapping("/foo3")
	public ListenableFuture<Object> foo3() {
		return service.foo3();
	}

	@GetMapping("/foo4")
	public Mono<Object> foo4() {
		var foo1 = web.get().uri(url + "/foo1").retrieve().bodyToMono(Map.class);
		var foo2 = web.get().uri(url + "/foo2").retrieve().bodyToMono(Map.class);
		var foo3 = web.get().uri(url + "/foo3").retrieve().bodyToMono(Map.class);
		return Mono.zip(foo1, foo2, foo3)
				.map(t -> Map.of("foo1", t.getT1(), "foo2", t.getT2(), "foo3", t.getT3()));
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
