package com.example.common;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.annotation.OnSkipInProcess;
import org.springframework.batch.core.annotation.OnSkipInRead;
import org.springframework.batch.core.annotation.OnSkipInWrite;
import org.springframework.boot.autoconfigure.batch.JobExecutionExitCodeGenerator;
import org.springframework.stereotype.Component;

@Component
public class ExitCodeGeneratorImpl extends JobExecutionExitCodeGenerator {

	private boolean warning;

	@Override
	public int getExitCode() {
		if (warning) {
			return BatchStatus.ABANDONED.ordinal();
		}
		return super.getExitCode();
	}

	@OnSkipInRead
	public void onSkipInRead(Throwable t) {
		warning = true;
	}

	@OnSkipInProcess
	public void onSkipInProcess(Object item, Throwable t) {
		warning = true;
	}

	@OnSkipInWrite
	public void onSkipInWrite(Object item, Throwable t) {
		warning = true;
	}
}
