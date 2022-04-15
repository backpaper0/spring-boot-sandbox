package com.example;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.integration.launch.JobLaunchingGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.endpoint.MethodInvokingMessageSource;

@Configuration
public class CountConfig {

	private final StepBuilderFactory steps;
	private final JobBuilderFactory jobs;
	private final JobRepository jobRepository;

	public CountConfig(StepBuilderFactory steps, JobBuilderFactory jobs, JobRepository jobRepository) {
		this.steps = steps;
		this.jobs = jobs;
		this.jobRepository = jobRepository;
	}

	@Bean
	@StepScope
	public PrintCountTask printCountTask() {
		return new PrintCountTask();
	}

	@Bean
	public Step printCountStep() {
		return steps.get("PrintCount").tasklet(printCountTask()).build();
	}

	@Bean
	public Job printCountJob() {
		return jobs.get("PrintCount").start(printCountStep()).build();
	}

	@Bean
	public CountToJobLaunchRequestTransformer countToJobLaunchRequestTransformer() {
		return new CountToJobLaunchRequestTransformer(printCountJob());
	}

	@Bean
	public SimpleJobLauncher simpleJobLauncher() {
		SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
		jobLauncher.setJobRepository(jobRepository);
		return jobLauncher;
	}

	@Bean
	public JobLaunchingGateway jobLaunchingGateway() {
		return new JobLaunchingGateway(simpleJobLauncher());
	}

	@Bean
	public MethodInvokingMessageSource countMessageSource() {
		MethodInvokingMessageSource messageSource = new MethodInvokingMessageSource();
		messageSource.setObject(new AtomicLong());
		messageSource.setMethodName("getAndIncrement");
		return messageSource;
	}

	@Bean
	public IntegrationFlow integrationFlow() {
		return IntegrationFlows
				.from(countMessageSource())
				.transform(countToJobLaunchRequestTransformer())
				.handle(jobLaunchingGateway())
				.log()
				.get();
	}
}
