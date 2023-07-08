package com.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
public class ExampleController {

	private final Counter counter;

	public ExampleController(Counter counter) {
		this.counter = counter;
	}

	@GetMapping("/foo")
	public String getFoo() {
		return "foo";
	}

	@GetMapping("/bar")
	public String getBar() {
		return "bar";
	}

	@PostMapping("/count")
	public SseEmitter getCount(@RequestParam(defaultValue = "3") int n) {
		SseEmitter sseEmitter = new SseEmitter();
		counter.countUp(sseEmitter, Math.max(n, 3));
		return sseEmitter;
	}
}
