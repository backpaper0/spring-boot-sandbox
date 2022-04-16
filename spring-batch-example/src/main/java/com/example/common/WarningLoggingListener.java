package com.example.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.annotation.OnSkipInProcess;
import org.springframework.batch.core.annotation.OnSkipInRead;
import org.springframework.batch.core.annotation.OnSkipInWrite;
import org.springframework.stereotype.Component;

@Component
public class WarningLoggingListener {

	private static final Logger logger = LoggerFactory.getLogger(WarningLoggingListener.class);

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
