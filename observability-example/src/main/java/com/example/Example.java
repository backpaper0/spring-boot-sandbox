package com.example;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;

@Component
public class Example {

	private final ObservationRegistry registry;

	public Example(ObservationRegistry registry) {
		this.registry = registry;
	}

	@Scheduled(cron = "*/10 * * * * *")
	public void run() {
		Observation.createNotStarted("foo", registry)
				.lowCardinalityKeyValue("lowTag", "lowTagValue")
				.highCardinalityKeyValue("highTag", "highTagValue")
				.observe(() -> {
					sleep(100);
					runInternal();
					sleep(100);
				});
	}

	private void runInternal() {
		Observation.createNotStarted("bar", registry)
				.lowCardinalityKeyValue("lowTag", "lowTagValue")
				.highCardinalityKeyValue("highTag", "highTagValue")
				.observe(() -> {
					sleep(100);
					System.out.println("Hello");
					sleep(100);
				});
	}

	static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}