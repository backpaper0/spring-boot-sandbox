package com.example.profile.impl;

import org.springframework.batch.core.StepExecutionListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.example.profile.SwitchListenerByProfileExampleListenerBuilder;

@Component
@Profile("default")
public class DefaultSwitchListenerByProfileExampleListenerBuilder
		implements SwitchListenerByProfileExampleListenerBuilder {

	@Override
	public StepExecutionListener build() {
		return new StepExecutionListener() {
		};
	}
}
