package com.example.tasklet;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TaskletDemoBatch {

	private final StepBuilderFactory steps;
	private final JobBuilderFactory jobs;

	public TaskletDemoBatch(StepBuilderFactory steps, JobBuilderFactory jobs) {
		this.steps = steps;
		this.jobs = jobs;
	}

	@Bean
	@StepScope
	public TaskletDemoTasklet taskletDemoTasklet() {
		return new TaskletDemoTasklet();
	}

	@Bean
	public Step taskletDemoStep() {
		return steps.get("TaskletDemo")
				.tasklet(taskletDemoTasklet())
				.build();
	}

	@Bean
	public Job taskletDemoJob() {
		return jobs.get("TaskletDemo")
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
