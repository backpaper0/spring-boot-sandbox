package com.example.parameter;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.job.builder.JobBuilder;
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
public class ParameterExampleConfig {

	@Autowired
	private JobRepository jobRepository;
	@Autowired
	private PlatformTransactionManager transactionManager;

	@Autowired
	private MyParameterSupplier myParameterSupplier;

	@Bean
	public ParameterExampleTasklet parameterExampleTasklet() {
		return new ParameterExampleTasklet(myParameterSupplier);
	}

	@Bean
	public Step parameterExampleStep() {
		return new StepBuilder("parameterExampleStep", jobRepository)
				.tasklet(parameterExampleTasklet(), transactionManager)
				.build();
	}

	@Bean
	public Job parameterExampleJob() {
		return new JobBuilder("parameterExampleJob", jobRepository)
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
