package com.example.misc;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.item.support.ListItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import lombok.Setter;

/**
 * 1つ前のItemを参照する{@link ItemProcessor}の例。
 *
 */
@SpringBootTest
public class CurrentAndPrevItemTest {

	@Autowired
	JobLauncher jobLauncher;
	@Autowired
	TestConfig config;
	private JobParameters jobParameters;

	@BeforeEach
	void init() {
		config.itemWriter().getWrittenItems().clear();
		jobParameters = new JobParametersBuilder()
				.addString("uuid", UUID.randomUUID().toString())
				.toJobParameters();
	}

	@Test
	void test() throws Exception {
		config.setItems(List.of(1, 2, 3, 4, 5));
		jobLauncher.run(config.job(), jobParameters);
		assertEquals(config.itemWriter().getWrittenItems(), List.of(1, 2, 3, 4, 5));
	}

	@Test
	void test2() throws Exception {
		config.setItems(List.of(1, 2, 4, 3, 5));
		JobExecution jobExecution = jobLauncher.run(config.job(), jobParameters);

		assertEquals(1, jobExecution.getAllFailureExceptions().size());
		Throwable exception = jobExecution.getAllFailureExceptions().get(0);
		assertEquals("ERROR!", exception.getMessage());
	}

	@TestConfiguration
	static class TestConfig {

		@Autowired
		StepBuilderFactory steps;
		@Autowired
		JobBuilderFactory jobs;

		@Setter
		private List<Integer> items;

		@Bean
		@StepScope
		public ListItemReader<Integer> itemReader() {
			return new ListItemReader<>(items);
		}

		@Bean
		@StepScope
		public CurrentAndPrevItemProcessor itemProcessor() {
			return new CurrentAndPrevItemProcessor();
		}

		@Bean
		public ListItemWriter<Integer> itemWriter() {
			return new ListItemWriter<>();
		}

		@Bean
		public Step step() {
			return steps.get("test")
					.<Integer, Integer> chunk(3)
					.reader(itemReader())
					.processor(itemProcessor())
					.writer(itemWriter())
					.build();
		}

		@Bean
		public Job job() {
			return jobs.get("test")
					.start(step())
					.build();
		}
	}
}
