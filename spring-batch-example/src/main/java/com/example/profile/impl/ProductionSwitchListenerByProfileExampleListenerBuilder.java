package com.example.profile.impl;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.example.profile.SwitchListenerByProfileExampleListenerBuilder;

@Component
@Profile("!default")
public class ProductionSwitchListenerByProfileExampleListenerBuilder
		implements SwitchListenerByProfileExampleListenerBuilder {

	@Override
	public StepExecutionListener build() {
		return new MyStepExecutionListener();
	}

	private static class MyStepExecutionListener implements StepExecutionListener {

		@Override
		public void beforeStep(StepExecution stepExecution) {
			System.out.println("StepExecutionListener#beforeStep");
		}

		@Override
		public ExitStatus afterStep(StepExecution stepExecution) {
			System.out.println("StepExecutionListener#afterStep");
			return null;
		}
	}
}
