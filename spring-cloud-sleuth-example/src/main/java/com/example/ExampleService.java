package com.example;

import java.util.Map;

import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

@Service
public class ExampleService {

	public Object foo1() {
		return Map.of("message", "foo 1");
	}

	@NewSpan
	public Object foo2() {
		return Map.of("message", "foo 2");
	}

	@Async
	public ListenableFuture<Object> foo3() {
		return AsyncResult.forValue(Map.of("message", "foo 3"));
	}
}
