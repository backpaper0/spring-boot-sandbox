package com.example.pallarel;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class PallarelExample {

	private final JobBuilderFactory jobs;
	private final StepBuilderFactory steps;

	public PallarelExample(JobBuilderFactory jobs, StepBuilderFactory steps) {
		this.jobs = jobs;
		this.steps = steps;
	}

	@Bean
	public TaskExecutor pallarelTaskExecutor() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(4);
		taskExecutor.setMaxPoolSize(4);
		return taskExecutor;
	}

	@Bean
	public Job pallarelJob() {
		return jobs.get(PallarelExample.class.getSimpleName() + "Job")
				.start(pallarelFlow1())
				.build().build();
	}

	@Bean
	public Flow pallarelFlow1() {
		return new FlowBuilder<SimpleFlow>(PallarelExample.class.getSimpleName() + "Flow1")
				.split(pallarelTaskExecutor())
				.add(pallarelFlow2(), pallarelFlow3())
				.build();
	}

	@Bean
	public Flow pallarelFlow2() {
		return new FlowBuilder<SimpleFlow>(PallarelExample.class.getSimpleName() + "Flow2")
				.start(pallarelStep1())
				.next(pallarelStep2())
				.build();
	}

	@Bean
	public Flow pallarelFlow3() {
		return new FlowBuilder<SimpleFlow>(PallarelExample.class.getSimpleName() + "Flow3")
				.start(pallarelStep3())
				.build();
	}

	@Bean
	public Step pallarelStep1() {
		var counter = new AtomicInteger();
		return steps.get(PallarelExample.class.getSimpleName() + "Step1")
				.tasklet((contribution, chunkContext) -> {
					int i = counter.incrementAndGet();
					System.out.printf("[%s] %s: %s%n", Thread.currentThread().getName(), "Step1",
							i);
					return i < 4 ? RepeatStatus.CONTINUABLE : RepeatStatus.FINISHED;
				}).build();
	}

	@Bean
	public Step pallarelStep2() {
		var counter = new AtomicInteger();
		return steps.get(PallarelExample.class.getSimpleName() + "Step2")
				.tasklet((contribution, chunkContext) -> {
					int i = counter.incrementAndGet();
					System.out.printf("[%s] %s: %s%n", Thread.currentThread().getName(), "Step2",
							i);
					return i < 3 ? RepeatStatus.CONTINUABLE : RepeatStatus.FINISHED;
				}).build();
	}

	@Bean
	public Step pallarelStep3() {
		return steps.get(PallarelExample.class.getSimpleName() + "Step3")
				.tasklet((contribution, chunkContext) -> {
					System.out.printf("[%s] %s%n", Thread.currentThread().getName(), "Step3");
					return null;
				}).build();
	}
}
