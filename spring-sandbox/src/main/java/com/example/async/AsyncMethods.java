package com.example.async;

import java.util.concurrent.Future;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

@Component
public class AsyncMethods {

	@Async
	public Future<String> method1() {
		return AsyncResult.forValue(Thread.currentThread().getName());
	}

	@Async("altExecutor")
	public Future<String> method2() {
		return AsyncResult.forValue(Thread.currentThread().getName());
	}
}
