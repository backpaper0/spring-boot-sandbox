package com.example;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;

@RestController
public class CountController {

	private final AtomicInteger counter1 = new AtomicInteger();
	private final AtomicInteger counter2;
	private final Counter counter3;
	private final Counter counter4a;
	private final Counter counter4b;
	private final Counter counter4c;

	public CountController(MeterRegistry registry) {
		Gauge.builder("example.count.1", this.counter1::get).register(registry);
		this.counter2 = registry.gauge("example.count.2", new AtomicInteger());
		this.counter3 = registry.counter("example.count.3");
		this.counter4a = registry.counter("example.count.4", "example.name", "foo");
		this.counter4b = registry.counter("example.count.4", "example.name", "bar");
		this.counter4c = registry.counter("example.count.4", "example.name", "baz");
	}

	@GetMapping("/count")
	public Object count() {
		counter1.incrementAndGet();
		counter2.incrementAndGet();
		counter3.increment();
		counter4a.increment();
		counter4b.increment();
		counter4c.increment();
		return Map.of("counter1", counter1.get(),
				"counter2", counter2.get(),
				"counter3", counter3.count());
	}
}
