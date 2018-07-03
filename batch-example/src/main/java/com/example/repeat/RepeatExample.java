package com.example.repeat;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepeatExample {

    private final JobBuilderFactory jobs;
    private final StepBuilderFactory steps;
    private final SimpleRepeatTasklet simpleRepeatTasklet;

    public RepeatExample(final JobBuilderFactory jobs, final StepBuilderFactory steps,
            final SimpleRepeatTasklet simpleRepeatTasklet) {
        this.jobs = jobs;
        this.steps = steps;
        this.simpleRepeatTasklet = simpleRepeatTasklet;
    }

    @Bean
    public Job simpleRepeatJob() {
        return jobs.get("simpleRepeatJob").start(simpleRepeatStep()).build();
    }

    @Bean
    public Step simpleRepeatStep() {
        return steps.get("simpleRepeatStep").tasklet(simpleRepeatTasklet).build();
    }
}
