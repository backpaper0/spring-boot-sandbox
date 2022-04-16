package com.example.file2db;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.SkipListener;
import org.springframework.boot.autoconfigure.batch.JobExecutionExitCodeGenerator;

public class ExitCodeGeneratorImpl<I, O> extends JobExecutionExitCodeGenerator implements SkipListener<I, O> {

	private boolean warning;

	@Override
	public int getExitCode() {
		if (warning) {
			return BatchStatus.ABANDONED.ordinal();
		}
		return super.getExitCode();
	}

	@Override
	public void onSkipInRead(Throwable t) {
		warning = true;
	}

	@Override
	public void onSkipInProcess(I item, Throwable t) {
		warning = true;
	}

	@Override
	public void onSkipInWrite(O item, Throwable t) {
		warning = true;
	}
}
