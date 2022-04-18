package com.example.misc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class DemoItemProcessor implements ItemProcessor<Integer, Integer> {

	private static final Logger logger = LoggerFactory.getLogger(DemoItemProcessor.class);

	@Override
	public Integer process(Integer item) throws Exception {
		logger.info("processor#process: {}", item);
		return item;
	}
}