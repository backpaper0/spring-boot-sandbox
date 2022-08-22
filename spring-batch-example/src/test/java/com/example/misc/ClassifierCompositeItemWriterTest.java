package com.example.misc;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.item.support.ListItemWriter;
import org.springframework.batch.item.support.PassThroughItemProcessor;
import org.springframework.batch.item.support.builder.ClassifierCompositeItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.classify.Classifier;
import org.springframework.context.annotation.Bean;

/**
 * {@link ClassifierCompositeItemWriter}を使って1〜5と6〜10を別の
 * {@link ItemWriter}で処理する例。
 *
 */
@SpringBootTest
public class ClassifierCompositeItemWriterTest {

	@Autowired
	JobLauncher jobLauncher;
	@Autowired
	TestConfig config;

	@Test
	void test() throws Exception {
		jobLauncher.run(config.job(), new JobParameters());
		assertEquals(
				IntStream.rangeClosed(1, 5).boxed().toList(),
				config.itemWriter1().getWrittenItems());
		assertEquals(
				IntStream.rangeClosed(6, 10).boxed().toList(),
				config.itemWriter2().getWrittenItems());
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
		public ClassifierCompositeItemWriter<Integer> itemWriter() {
			Classifier<Integer, ItemWriter<? super Integer>> classifier = classifiable -> {
				if (classifiable <= 5) {
					return itemWriter1();
				}
				return itemWriter2();
			};
			return new ClassifierCompositeItemWriterBuilder<Integer>()
					.classifier(classifier)
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
