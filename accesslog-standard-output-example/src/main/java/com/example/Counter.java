package com.example;

import java.util.concurrent.TimeUnit;

import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter.SseEventBuilder;

@Component
public class Counter {

	@Async
	public void countUp(SseEmitter sseEmitter, int n) {
		Exception exception = null;
		try {
			for (int i = 0; i < n; i++) {
				TimeUnit.SECONDS.sleep(1);
				SseEventBuilder data = SseEmitter.event()
						.data(String.valueOf(i + 1), MediaType.TEXT_PLAIN);
				sseEmitter.send(data);
			}
		} catch (Exception e) {
			exception = e;
		} finally {
			if (exception != null) {
				sseEmitter.completeWithError(exception);
			} else {
				sseEmitter.complete();
			}
		}
	}
}
