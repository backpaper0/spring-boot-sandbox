package com.example.misc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamWriter;

public class SleepItemWriter<T> implements ItemStreamWriter<T> {

	private static final Logger logger = LoggerFactory.getLogger(SleepItemWriter.class);
	private final ItemStreamWriter<T> delegate;
	private final long sleepTimeMillis;

	public SleepItemWriter(ItemStreamWriter<T> delegate, long sleepTimeMillis) {
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
	public void write(Chunk<? extends T> chunk) throws Exception {
		delegate.write(chunk);
		sleep();
	}
}
