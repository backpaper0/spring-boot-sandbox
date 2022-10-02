package com.example.misc;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.item.support.ListItemWriter;
import org.springframework.batch.item.support.PassThroughItemProcessor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@SpringBootTest
public class AddListenerWithBeanPostProcessorTest {

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
	@Import(MyListenerRegister.class)
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
					.listener(new MyListener("foo"))
					.listener(new MyListener("bar"))
					.build();
		}

		@Bean
		public Job job() {
			return jobs.get("test")
					.start(step())
					.build();
		}
	}

	@TestComponent
	static class MyListenerRegister implements BeanPostProcessor {
		@Override
		public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
			if (bean instanceof TaskletStep) {
				TaskletStep step = (TaskletStep) bean;
				step.registerStepExecutionListener(new MyListener("baz"));
			}
			return bean;
		}
	}

	static class MyListener implements StepExecutionListener {

		private final String name;

		public MyListener(String name) {
			this.name = name;
		}

		@Override
		public void beforeStep(StepExecution stepExecution) {
			System.out.println("[" + name + "]beforeStep");
		}

		@Override
		public ExitStatus afterStep(StepExecution stepExecution) {
			System.out.println("[" + name + "]afterStep");
			return null;
		}
	}
}
