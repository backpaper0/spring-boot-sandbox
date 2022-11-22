package com.example.flow;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FlowExample {

	private final JobBuilderFactory jobs;
	private final StepBuilderFactory steps;

	public FlowExample(final JobBuilderFactory jobs, final StepBuilderFactory steps) {
		this.jobs = jobs;
		this.steps = steps;
	}

	@Bean
	public Job flowExampleJob() {
		return jobs.get("flowExampleJob")
				.start(flowExampleStep1())
				.on("FAILED").to(flowExampleStep2())
				.from(flowExampleStep1()).on("*").to(flowExampleStep3())
				.end()
				.build();
	}

	@Bean
	public Step flowExampleStep1() {
		return steps.get("flowExampleStep1").tasklet(flowExampleTasklet1()).build();
	}

	@Bean
	public Step flowExampleStep2() {
		return steps.get("flowExampleStep2").tasklet(flowExampleTasklet2()).build();
	}

	@Bean
	public Step flowExampleStep3() {
		return steps.get("flowExampleStep3").tasklet(flowExampleTasklet3()).build();
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
