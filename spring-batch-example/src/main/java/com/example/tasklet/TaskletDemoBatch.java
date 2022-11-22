package com.example.tasklet;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class TaskletDemoBatch {

	@Autowired
	private JobRepository jobRepository;
	@Autowired
	private PlatformTransactionManager transactionManager;

	@Bean
	@StepScope
	public TaskletDemoTasklet taskletDemoTasklet() {
		return new TaskletDemoTasklet();
	}

	@Bean
	public Step taskletDemoStep() {
		return new StepBuilder("TaskletDemo", jobRepository)
				.tasklet(taskletDemoTasklet(), transactionManager)
				.build();
	}

	@Bean
	public Job taskletDemoJob() {
		return new JobBuilder("TaskletDemo", jobRepository)
				.start(taskletDemoStep())
				.incrementer(new RunIdIncrementer())
				.build();
	}

	static class TaskletDemoTasklet implements Tasklet {

		private static final Logger logger = LoggerFactory.getLogger(TaskletDemoTasklet.class);
		private final AtomicInteger counter = new AtomicInteger();
		private final int size = 3;

		@Override
		public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
			RepeatStatus repeatStatus = counter.getAndIncrement() < size
					? RepeatStatus.CONTINUABLE
					: RepeatStatus.FINISHED;
			logger.info("tasklet#execute: {}", repeatStatus);
			return repeatStatus;
		}
	}
}
