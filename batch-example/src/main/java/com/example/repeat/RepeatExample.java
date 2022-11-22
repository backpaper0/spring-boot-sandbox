package com.example.repeat;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.PlatformTransactionManager;

@SpringBootApplication
public class RepeatExample {

	private final JobRepository jobRepository;
	private final PlatformTransactionManager transactionManager;
	private final SimpleRepeatTasklet simpleRepeatTasklet;

	public RepeatExample(JobRepository jobRepository, PlatformTransactionManager transactionManager,
			final SimpleRepeatTasklet simpleRepeatTasklet) {
		this.jobRepository = jobRepository;
		this.transactionManager = transactionManager;
		this.simpleRepeatTasklet = simpleRepeatTasklet;
	}

	@Bean
	public Job simpleRepeatJob() {
		return new JobBuilder("simpleRepeatJob", jobRepository).start(simpleRepeatStep()).build();
	}

	@Bean
	public Step simpleRepeatStep() {
		return new StepBuilder("simpleRepeatStep", jobRepository)
				.tasklet(simpleRepeatTasklet, transactionManager).build();
	}
}
