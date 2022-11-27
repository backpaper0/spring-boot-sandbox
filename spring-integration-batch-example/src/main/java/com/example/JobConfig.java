package com.example;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class JobConfig {

	private final JobRepository jobRepository;
	private final PlatformTransactionManager transactionManager;

	public JobConfig(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		this.jobRepository = jobRepository;
		this.transactionManager = transactionManager;
	}

	@Bean
	@StepScope
	public PrintCountTask printCountTask() {
		return new PrintCountTask();
	}

	@Bean
	public Step printCountStep() {
		return new StepBuilder("PrintCount", jobRepository)
				.tasklet(printCountTask(), transactionManager)
				.build();
	}

	@Bean
	public Job printCountJob() {
		return new JobBuilder("PrintCount", jobRepository)
				.start(printCountStep())
				.build();
	}
}
