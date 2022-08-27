package com.example.misc;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.item.ItemCountAware;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.support.ListItemWriter;
import org.springframework.batch.item.support.PassThroughItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.PathResource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SpringBootTest
public class ItemCountAwareTest {

	@Autowired
	JobLauncher jobLauncher;
	@Autowired
	TestConfig config;

	@Test
	void test() throws Exception {
		jobLauncher.run(config.job(), new JobParameters());

		List<MyItem> expected = List.of(
				new MyItem(1, "foo"),
				new MyItem(2, "bar"),
				new MyItem(3, "baz"),
				new MyItem(4, "qux"),
				new MyItem(5, "quux"),
				new MyItem(6, "corge"),
				new MyItem(7, "grault"),
				new MyItem(8, "garply"),
				new MyItem(9, "waldo"),
				new MyItem(10, "fred"),
				new MyItem(11, "plugh"),
				new MyItem(12, "xyzzy"),
				new MyItem(13, "thud"));

		assertEquals(expected, config.itemWriter().getWrittenItems());
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class MyItem implements ItemCountAware {

		private int itemCount;
		private String value;
	}

	@TestConfiguration
	static class TestConfig {

		@Autowired
		StepBuilderFactory steps;
		@Autowired
		JobBuilderFactory jobs;

		@Bean
		public FlatFileItemReader<MyItem> itemReader() {
			return new FlatFileItemReaderBuilder<MyItem>()
					.saveState(false)
					.resource(new PathResource("inputs/foobar.txt"))
					.delimited()
					.delimiter(",")
					.names("value")
					.targetType(MyItem.class)
					.build();
		}

		@Bean
		public PassThroughItemProcessor<MyItem> itemProcessor() {
			return new PassThroughItemProcessor<>();
		}

		@Bean
		public ListItemWriter<MyItem> itemWriter() {
			return new ListItemWriter<>();
		}

		@Bean
		public Step step() {
			return steps.get("test")
					.<MyItem, MyItem> chunk(3)
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
