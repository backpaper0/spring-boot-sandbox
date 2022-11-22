package com.example.flow;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.PlatformTransactionManager;

@SpringBootApplication
public class FlowExample {

	private final JobRepository jobRepository;
	private final PlatformTransactionManager transactionManager;

	public FlowExample(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		this.jobRepository = jobRepository;
		this.transactionManager = transactionManager;
	}

	@Bean
	public Job flowExampleJob() {
		return new JobBuilder("flowExampleJob", jobRepository)
				.start(flowExampleStep1())
				.on("FAILED").to(flowExampleStep2())
				.from(flowExampleStep1()).on("*").to(flowExampleStep3())
				.end()
				.build();
	}

	@Bean
	public Step flowExampleStep1() {
		return new StepBuilder("flowExampleStep1", jobRepository)
				.tasklet(flowExampleTasklet1(), transactionManager).build();
	}

	@Bean
	public Step flowExampleStep2() {
		return new StepBuilder("flowExampleStep2", jobRepository)
				.tasklet(flowExampleTasklet2(), transactionManager).build();
	}

	@Bean
	public Step flowExampleStep3() {
		return new StepBuilder("flowExampleStep3", jobRepository)
				.tasklet(flowExampleTasklet3(), transactionManager).build();
	}

	@Bean
	public Tasklet flowExampleTasklet1() {
		return (contribution, chunkContext) -> {
			System.out.println("Flow 1");
			//            contribution.setExitStatus(ExitStatus.FAILED);
			return RepeatStatus.FINISHED;
		};
	}

	@Bean
	public Tasklet flowExampleTasklet2() {
		return (contribution, chunkContext) -> {
			System.out.println("Flow 2");
			return RepeatStatus.FINISHED;
		};
	}

	@Bean
	public Tasklet flowExampleTasklet3() {
		return (contribution, chunkContext) -> {
			System.out.println("Flow 3");
			return RepeatStatus.FINISHED;
		};
	}
}
