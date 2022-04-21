package com.example;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SchedulingExample {

	private final SchedulingTerminator terminator;

	public SchedulingExample(SchedulingTerminator terminator) {
		this.terminator = terminator;
	}

	@Scheduled(cron = "${app.cron}", zone = "${app.zone}")
	public void run() throws Exception {

		if (terminator.checkTerminationStatus()) {
			return;
		}

		System.out.printf("%s - %s%n", Thread.currentThread().getName(), LocalDateTime.now());
	}
}
