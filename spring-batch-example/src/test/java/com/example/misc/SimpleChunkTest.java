package com.example.misc;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.item.support.ListItemWriter;
import org.springframework.batch.item.support.PassThroughItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootTest
public class SimpleChunkTest {

	@Autowired
	JobLauncher jobLauncher;
	@Autowired
	TestConfig config;

	@Test
	void test() throws Exception {
		jobLauncher.run(config.job(), new JobParameters());

		List<Integer> list = IntStream.rangeClosed(1, 10).boxed().toList();
		assertEquals(list, config.itemWriter().getWrittenItems());
	}

	@TestConfiguration
	static class TestConfig {

		@Autowired
		StepBuilderFactory steps;
		@Autowired
		JobBuilderFactory jobs;

		@Bean
		public ListItemReader<Integer> itemReader() {
			return new ListItemReader<>(IntStream.rangeClosed(1, 10).boxed().toList());
		}

		@Bean
		public PassThroughItemProcessor<Integer> itemProcessor() {
			return new PassThroughItemProcessor<>();
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
