package com.example.misc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class SleepItemProcessor<I, O> implements ItemProcessor<I, O> {

	private static final Logger logger = LoggerFactory.getLogger(SleepItemProcessor.class);
	private final ItemProcessor<I, O> delegate;
	private final long sleepTimeMillis;

	public SleepItemProcessor(ItemProcessor<I, O> delegate, long sleepTimeMillis) {
		this.delegate = delegate;
		this.sleepTimeMillis = sleepTimeMillis;
	}

	private void sleep() {
		try {
			Thread.sleep(sleepTimeMillis);
		} catch (InterruptedException e) {
			logger.warn("", e);
		}
	}

	@Override
	public O process(I item) throws Exception {
		O value = delegate.process(item);
		sleep();
		return value;
	}
}
