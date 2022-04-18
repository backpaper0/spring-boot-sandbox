package com.example.multithread;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.example.misc.DemoItemProcessor;
import com.example.misc.DemoItemReader;
import com.example.misc.DemoItemWriter;
import com.example.misc.SleepItemProcessor;
import com.example.misc.SleepItemReader;
import com.example.misc.SleepItemWriter;

@Configuration
public class MultiThreadDemoBatch {

	private final StepBuilderFactory steps;
	private final JobBuilderFactory jobs;

	public MultiThreadDemoBatch(StepBuilderFactory steps, JobBuilderFactory jobs) {
		this.steps = steps;
		this.jobs = jobs;
	}

	@Bean
	@StepScope
	public DemoItemReader multiThreadDemoItemReader() {
		return new DemoItemReader(5);
	}

	@Bean
	@StepScope
	public DemoItemProcessor multiThreadDemoItemProcessor() {
		return new DemoItemProcessor();
	}

	@Bean
	@StepScope
	public DemoItemWriter multiThreadDemoItemWriter() {
		return new DemoItemWriter();
	}

	@Bean
	public TaskExecutor multiThreadDemoTaskExecutor() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(Runtime.getRuntime().availableProcessors() - 1);
		return taskExecutor;
	}

	@Bean
	public Step multiThreadDemoStep(@Value("${app.multithread.enabled:true}") boolean enabledMultiThread) {
		return steps.get("MultiThreadDemo")
				.<Integer, Integer> chunk(3)
				.reader(new SleepItemReader<>(multiThreadDemoItemReader(), 1000))
				.processor(new SleepItemProcessor<>(multiThreadDemoItemProcessor(), 1000))
				.writer(new SleepItemWriter<>(multiThreadDemoItemWriter(), 1000))
				.taskExecutor(enabledMultiThread ? multiThreadDemoTaskExecutor() : new SyncTaskExecutor())
				.build();
	}

	@Bean
	public Job multiThreadDemoJob() {
		return jobs.get("MultiThreadDemo")
				.start(multiThreadDemoStep(false))
				.incrementer(new RunIdIncrementer())
				.build();
	}
}
