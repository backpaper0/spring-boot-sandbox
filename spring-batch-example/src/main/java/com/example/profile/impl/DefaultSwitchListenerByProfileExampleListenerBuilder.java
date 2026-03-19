package com.example.profile.impl;

import com.example.profile.SwitchListenerByProfileExampleListenerBuilder;
import org.springframework.batch.core.listener.StepExecutionListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("default")
public class DefaultSwitchListenerByProfileExampleListenerBuilder
        implements SwitchListenerByProfileExampleListenerBuilder {

    @Override
    public StepExecutionListener build() {
        return new StepExecutionListener() {};
    }
}
