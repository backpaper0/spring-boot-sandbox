package com.example.misc;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.JobContext;
import org.springframework.batch.core.scope.context.StepContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * {@link Value}でインジェクションできる値を確認する。
 * スコープに応じて{@link StepContext}が持つ値と{@link JobContext}が持つ値をインジェクションできるっぽい。
 *
 */
@SpringBootTest
public class InjectionExampleTest {

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

		@Autowired
		InjectionExampleTasklet1 tasklet1;
		@Autowired
		InjectionExampleTasklet2 tasklet2;

		@Bean
		public Step step1() {
			return new StepBuilder("test1", jobRepository)
					.tasklet(tasklet1, transactionManager)
					.build();
		}

		@Bean
		public Step step2() {
			return new StepBuilder("test2", jobRepository)
					.tasklet(tasklet2, transactionManager)
					.build();
		}

		@Bean
		public Job job() {
			return new JobBuilder("test", jobRepository)
					.start(step1())
					.next(step2())
					.build();
		}
	}
}
