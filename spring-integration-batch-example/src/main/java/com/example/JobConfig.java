package com.example;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobConfig {

	private final StepBuilderFactory steps;
	private final JobBuilderFactory jobs;

	public JobConfig(StepBuilderFactory steps, JobBuilderFactory jobs) {
		this.steps = steps;
		this.jobs = jobs;
	}

	@Bean
	@StepScope
	public PrintCountTask printCountTask() {
		return new PrintCountTask();
	}

	@Bean
	public Step printCountStep() {
		return steps.get("PrintCount").tasklet(printCountTask()).build();
	}

	@Bean
	public Job printCountJob() {
		return jobs.get("PrintCount").start(printCountStep()).build();
	}
}
