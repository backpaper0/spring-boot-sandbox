package com.example.parameter;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ParameterExampleConfig {

	@Autowired
	private MyParameterSupplier myParameterSupplier;

	private final StepBuilderFactory steps;
	private final JobBuilderFactory jobs;

	public ParameterExampleConfig(StepBuilderFactory steps, JobBuilderFactory jobs) {
		this.steps = steps;
		this.jobs = jobs;
	}

	@Bean
	public ParameterExampleTasklet parameterExampleTasklet() {
		return new ParameterExampleTasklet(myParameterSupplier);
	}

	@Bean
	public Step parameterExampleStep() {
		return steps.get("parameterExampleStep")
				.tasklet(parameterExampleTasklet())
				.build();
	}

	@Bean
	public Job parameterExampleJob() {
		return jobs.get("parameterExampleJob")
				.start(parameterExampleStep())
				.build();
	}

	static class ParameterExampleTasklet implements Tasklet {

		private final MyParameterSupplier myParameterSupplier;
		private String value;

		public ParameterExampleTasklet(MyParameterSupplier myParameterSupplier) {
			this.myParameterSupplier = myParameterSupplier;
		}

		@Override
		public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
			value = myParameterSupplier.get();
			return RepeatStatus.FINISHED;
		}

		public String getValue() {
			return value;
		}
	}
}
