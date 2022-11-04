package com.example.repeat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
public class SimpleRepeatTasklet implements Tasklet {

	private static final Logger logger = LoggerFactory.getLogger(SimpleRepeatTasklet.class);

	private final Counter counter;

	public SimpleRepeatTasklet(final Counter counter) {
		this.counter = counter;
	}

	@Override
	public RepeatStatus execute(final StepContribution contribution,
			final ChunkContext chunkContext)
			throws Exception {
		counter.increment();
		final int value = counter.getValue();
		if (value < 10) {
			if (logger.isInfoEnabled()) {
				logger.info("Continue {}", value);
			}
			return RepeatStatus.CONTINUABLE;
		}
		if (logger.isInfoEnabled()) {
			logger.info("Finish {}", value);
		}
		return RepeatStatus.FINISHED;
	}

	@Component
	@StepScope
	public static class Counter {

		private int value;

		public void increment() {
			value++;
		}

		public int getValue() {
			return value;
		}
	}
}
