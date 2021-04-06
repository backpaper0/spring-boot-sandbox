package com.example.multithreaded;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
@EnableBatchProcessing
public class MultiThreadedExample {

	private final JobBuilderFactory jobs;
	private final StepBuilderFactory steps;

	public MultiThreadedExample(JobBuilderFactory jobs, StepBuilderFactory steps) {
		this.jobs = jobs;
		this.steps = steps;
	}

	@Bean
	public Job multiThreadedJob() {
		return jobs.get(MultiThreadedExample.class.getSimpleName() + "Job")
				.start(multiThreadedStep()).build();
	}

	@Bean
	public Step multiThreadedStep() {
		return steps.get(MultiThreadedExample.class.getSimpleName() + "Step")
				.<Integer, Integer> chunk(3)
				.reader(multiThreadedReader())
				.writer(multiThreadedWriter()).taskExecutor(multiThreadedTaskExecutor())
				.build();
	}

	@Bean
	public TaskExecutor multiThreadedTaskExecutor() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(2);
		taskExecutor.setMaxPoolSize(2);
		return taskExecutor;
	}

	@Bean
	public ItemReader<Integer> multiThreadedReader() {
		List<Integer> list = IntStream.range(1, 20).boxed().collect(Collectors.toList());
		Iterator<Integer> it = Collections.synchronizedCollection(list).iterator();
		return () -> {
			System.out.printf("[%s] read%n", Thread.currentThread().getName());
			return it.hasNext() ? it.next() : null;
		};
	}

	@Bean
	public ItemWriter<Integer> multiThreadedWriter() {
		return items -> {
			TimeUnit.MILLISECONDS.sleep(100);
			System.out.printf("[%s] write: %s%n", Thread.currentThread().getName(), items);
		};
	}
}
