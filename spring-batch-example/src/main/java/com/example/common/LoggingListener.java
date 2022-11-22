package com.example.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.annotation.AfterProcess;
import org.springframework.batch.core.annotation.AfterRead;
import org.springframework.batch.core.annotation.BeforeProcess;
import org.springframework.batch.core.annotation.BeforeWrite;
import org.springframework.batch.core.annotation.OnSkipInProcess;
import org.springframework.batch.core.annotation.OnSkipInRead;
import org.springframework.batch.core.annotation.OnSkipInWrite;
import org.springframework.batch.item.Chunk;
import org.springframework.stereotype.Component;

@Component
public class LoggingListener {

	private static final Logger logger = LoggerFactory.getLogger(LoggingListener.class);

	@AfterRead
	public void afterRead(Object item) {
		logger.info("read: {}", item);
	}

	@BeforeProcess
	public void beforeProcess(Object item) {
		logger.info("before process: {}", item);
	}

	@AfterProcess
	public void afterProcess(Object item, Object result) {
		logger.info("after process: {} -> {}", item, result);
	}

	@BeforeWrite
	public void beforeWrite(Chunk<Object> items) {
		logger.info("write: {}", items);
	}

	@OnSkipInRead
	public void onSkipInRead(Throwable t) {
		logger.warn(t.toString());
	}

	@OnSkipInProcess
	public void onSkipInProcess(Object item, Throwable t) {
		logger.warn(t.toString());
	}

	@OnSkipInWrite
	public void onSkipInWrite(Object item, Throwable t) {
		logger.warn(t.toString());
	}
}
