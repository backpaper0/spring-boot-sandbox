package com.example.multithread;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.misc.DemoItemProcessor;
import com.example.misc.DemoItemReader;
import com.example.misc.DemoItemWriter;
import com.example.misc.SleepItemProcessor;
import com.example.misc.SleepItemReader;
import com.example.misc.SleepItemWriter;

@Configuration
public class MultiThreadDemoBatch {

	@Autowired
	private JobRepository jobRepository;
	@Autowired
	private PlatformTransactionManager transactionManager;

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
		return new StepBuilder("MultiThreadDemo", jobRepository)
				.<Integer, Integer> chunk(3, transactionManager)
				.reader(new SleepItemReader<>(multiThreadDemoItemReader(), 1000))
				.processor(new SleepItemProcessor<>(multiThreadDemoItemProcessor(), 1000))
				.writer(new SleepItemWriter<>(multiThreadDemoItemWriter(), 1000))
				.taskExecutor(enabledMultiThread ? multiThreadDemoTaskExecutor() : new SyncTaskExecutor())
				.build();
	}

	@Bean
	public Job multiThreadDemoJob() {
		return new JobBuilder("MultiThreadDemo", jobRepository)
				.start(multiThreadDemoStep(false))
				.incrementer(new RunIdIncrementer())
				.build();
	}
}
