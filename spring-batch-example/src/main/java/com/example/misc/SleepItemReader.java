package com.example.misc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.infrastructure.item.ExecutionContext;
import org.springframework.batch.infrastructure.item.ItemStreamException;
import org.springframework.batch.infrastructure.item.ItemStreamReader;
import org.springframework.batch.infrastructure.item.NonTransientResourceException;
import org.springframework.batch.infrastructure.item.ParseException;
import org.springframework.batch.infrastructure.item.UnexpectedInputException;

public class SleepItemReader<T> implements ItemStreamReader<T> {

	private static final Logger logger = LoggerFactory.getLogger(SleepItemReader.class);
	private final ItemStreamReader<T> delegate;
	private final long sleepTimeMillis;

	public SleepItemReader(ItemStreamReader<T> delegate, long sleepTimeMillis) {
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
	public void open(ExecutionContext executionContext) throws ItemStreamException {
		delegate.open(executionContext);
		sleep();
	}

	@Override
	public void update(ExecutionContext executionContext) throws ItemStreamException {
		delegate.update(executionContext);
		sleep();
	}

	@Override
	public void close() throws ItemStreamException {
		delegate.close();
		sleep();
	}

	@Override
	public T read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		T value = delegate.read();
		sleep();
		return value;
	}
}
