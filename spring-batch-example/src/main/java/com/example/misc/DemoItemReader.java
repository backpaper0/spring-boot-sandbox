package com.example.misc;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamReader;

public class DemoItemReader implements ItemStreamReader<Integer> {

	private static final Logger logger = LoggerFactory.getLogger(DemoItemReader.class);
	private final AtomicInteger values = new AtomicInteger();
	private final int size;

	public DemoItemReader(int size) {
		this.size = size;
	}

	@Override
	public void open(ExecutionContext executionContext) {
		logger.info("reader#open");
	}

	@Override
	public void update(ExecutionContext executionContext) {
		logger.info("reader#update");
	}

	@Override
	public void close() {
		logger.info("reader#close");
	}

	@Override
	public Integer read() {
		Integer value = values.get() < size ? values.incrementAndGet() : null;
		logger.info("reader#read: {}", value);
		return value;
	}
}
