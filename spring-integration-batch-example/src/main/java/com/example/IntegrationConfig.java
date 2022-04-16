package com.example;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.integration.launch.JobLaunchingGateway;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.endpoint.MethodInvokingMessageSource;

@Configuration
public class IntegrationConfig {

	private final JobRepository jobRepository;
	private final List<Job> jobs;
	private final BatchProperties batchProperties;

	public IntegrationConfig(JobRepository jobRepository, List<Job> jobs, BatchProperties batchProperties) {
		this.jobRepository = jobRepository;
		this.jobs = jobs;
		this.batchProperties = batchProperties;
	}

	@Bean
	public CountToJobLaunchRequestTransformer countToJobLaunchRequestTransformer() {
		return new CountToJobLaunchRequestTransformer(jobs, batchProperties.getJob().getNames());
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
