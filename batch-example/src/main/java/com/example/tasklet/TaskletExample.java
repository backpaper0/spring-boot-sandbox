package com.example.tasklet;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.PlatformTransactionManager;

@SpringBootApplication
public class TaskletExample {

	private final JobRepository jobRepository;
	private final PlatformTransactionManager transactionManager;
	private final HelloTasklet tasklet;

	public TaskletExample(JobRepository jobRepository, PlatformTransactionManager transactionManager,
			final HelloTasklet tasklet) {
		this.jobRepository = jobRepository;
		this.transactionManager = transactionManager;
		this.tasklet = tasklet;
	}

	@Bean
	public Job taskletExampleJob() {
		return new JobBuilder("taskletExampleJob", jobRepository).start(taskletExampleStep()).build();
	}

	@Bean
	public Step taskletExampleStep() {
		return new StepBuilder("taskletExampleStep", jobRepository)
				.tasklet(tasklet, transactionManager).build();
	}
}
