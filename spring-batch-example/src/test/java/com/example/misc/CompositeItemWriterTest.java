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
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.item.support.ListItemWriter;
import org.springframework.batch.item.support.PassThroughItemProcessor;
import org.springframework.batch.item.support.builder.CompositeItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * {@link CompositeItemWriter}を使って複数の{@link ItemWriter}をまとめる例。
 *
 */
@SpringBootTest
public class CompositeItemWriterTest {

	@Autowired
	JobLauncher jobLauncher;
	@Autowired
	TestConfig config;

	@Test
	void test() throws Exception {
		jobLauncher.run(config.job(), new JobParameters());
		List<Integer> expected = IntStream.rangeClosed(1, 10).boxed().toList();
		assertEquals(expected, config.itemWriter1().getWrittenItems());
		assertEquals(expected, config.itemWriter2().getWrittenItems());
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
		public CompositeItemWriter<Integer> itemWriter() {
			return new CompositeItemWriterBuilder<Integer>()
					.delegates(
							itemWriter1(),
							itemWriter2())
					.build();
		}

		@Bean
		public ListItemWriter<Integer> itemWriter1() {
			return new ListItemWriter<>();
		}

		@Bean
		public ListItemWriter<Integer> itemWriter2() {
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
