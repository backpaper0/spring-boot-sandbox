package com.example.misc;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamWriter;

public class DemoItemWriter implements ItemStreamWriter<Integer> {

	private static final Logger logger = LoggerFactory.getLogger(DemoItemWriter.class);

	@Override
	public void open(ExecutionContext executionContext) {
		logger.info("writer#open");
	}

	@Override
	public void update(ExecutionContext executionContext) {
		logger.info("writer#update");
	}

	@Override
	public void close() {
		logger.info("writer#close");
	}

	@Override
	public void write(List<? extends Integer> items) {
		logger.info("writer#write: {}", items);
	}
}
