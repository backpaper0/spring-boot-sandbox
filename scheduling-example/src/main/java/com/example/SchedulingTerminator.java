package com.example;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

@Component
public class SchedulingTerminator {

	private static final Logger logger = LoggerFactory.getLogger(SchedulingTerminator.class);

	private final ThreadPoolTaskScheduler taskScheduler;
	private final AtomicBoolean termination = new AtomicBoolean();

	public SchedulingTerminator(ThreadPoolTaskScheduler taskScheduler) {
		this.taskScheduler = taskScheduler;
	}

	public boolean checkTerminationStatus() {
		if (termination.get()) {
			return true;
		} else if (triggerTermination()) {
			logger.info("Terminate...");
			termination.set(true);
			new Thread(taskScheduler::shutdown).start();
			return true;
		}
		return false;
	}

	private boolean triggerTermination() {
		try {
			return Files.deleteIfExists(Path.of("target/terminate"));
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
