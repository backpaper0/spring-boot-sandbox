package com.example.misc;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * {@link StepExecution}の変化を確認する。
 *
 */
@SpringBootTest
public class StepExecutionExampleTest {

	@Autowired
	JobLauncher jobLauncher;
	@Autowired
	TestConfig config;

	@Test
	void test() throws Exception {
		jobLauncher.run(config.job(), new JobParameters());
	}

	@TestConfiguration
	static class TestConfig {

		@Autowired
		private JobRepository jobRepository;
		@Autowired
		private PlatformTransactionManager transactionManager;

		@Bean
		@StepScope
		public StepExecutionExampleItemReader itemReader() {
			return new StepExecutionExampleItemReader();
		}

		@Bean
		@StepScope
		public StepExecutionExampleItemProcessor itemProcessor() {
			return new StepExecutionExampleItemProcessor();
		}

		@Bean
		@StepScope
		public StepExecutionExampleItemWriter itemWriter() {
			return new StepExecutionExampleItemWriter();
		}

		@Bean
		public Step step() {
			return new StepBuilder("test", jobRepository)
					.<Integer, Integer> chunk(3, transactionManager)
					.reader(itemReader())
					.processor(itemProcessor())
					.writer(itemWriter())
					.build();
		}

		@Bean
		public Job job() {
			return new JobBuilder("test", jobRepository)
					.start(step())
					.build();
		}
	}
}
