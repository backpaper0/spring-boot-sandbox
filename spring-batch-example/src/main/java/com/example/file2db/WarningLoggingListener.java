package com.example.file2db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.SkipListener;

public class WarningLoggingListener<I, O> implements SkipListener<I, O> {

	private static final Logger logger = LoggerFactory.getLogger(WarningLoggingListener.class);

	@Override
	public void onSkipInRead(Throwable t) {
		logger.warn(t.toString());
	}

	@Override
	public void onSkipInProcess(I item, Throwable t) {
		logger.warn(t.toString());
	}

	@Override
	public void onSkipInWrite(O item, Throwable t) {
		logger.warn(t.toString());
	}
}
