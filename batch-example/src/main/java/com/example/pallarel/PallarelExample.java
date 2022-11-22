package com.example.pallarel;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@SpringBootApplication
public class PallarelExample {

	private final JobRepository jobRepository;
	private final PlatformTransactionManager transactionManager;

	public PallarelExample(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		this.jobRepository = jobRepository;
		this.transactionManager = transactionManager;
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
		return new JobBuilder(PallarelExample.class.getSimpleName() + "Job", jobRepository)
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
		return new StepBuilder(PallarelExample.class.getSimpleName() + "Step1", jobRepository)
				.tasklet((contribution, chunkContext) -> {
					int i = counter.incrementAndGet();
					System.out.printf("[%s] %s: %s%n", Thread.currentThread().getName(), "Step1",
							i);
					return i < 4 ? RepeatStatus.CONTINUABLE : RepeatStatus.FINISHED;
				}, transactionManager).build();
	}

	@Bean
	public Step pallarelStep2() {
		var counter = new AtomicInteger();
		return new StepBuilder(PallarelExample.class.getSimpleName() + "Step2", jobRepository)
				.tasklet((contribution, chunkContext) -> {
					int i = counter.incrementAndGet();
					System.out.printf("[%s] %s: %s%n", Thread.currentThread().getName(), "Step2",
							i);
					return i < 3 ? RepeatStatus.CONTINUABLE : RepeatStatus.FINISHED;
				}, transactionManager).build();
	}

	@Bean
	public Step pallarelStep3() {
		return new StepBuilder(PallarelExample.class.getSimpleName() + "Step3", jobRepository)
				.tasklet((contribution, chunkContext) -> {
					System.out.printf("[%s] %s%n", Thread.currentThread().getName(), "Step3");
					return null;
				}, transactionManager).build();
	}
}
