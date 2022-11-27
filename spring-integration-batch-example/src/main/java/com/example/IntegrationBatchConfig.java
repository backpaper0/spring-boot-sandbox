package com.example;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.integration.launch.JobLaunchingGateway;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.endpoint.MethodInvokingMessageSource;

@Configuration
public class IntegrationBatchConfig {

	private final JobLauncher jobLauncher;
	private final List<Job> jobs;
	private final BatchProperties batchProperties;

	public IntegrationBatchConfig(JobLauncher jobLauncher, List<Job> jobs, BatchProperties batchProperties) {
		this.jobLauncher = jobLauncher;
		this.jobs = jobs;
		this.batchProperties = batchProperties;
	}

	@Bean
	public CountToJobLaunchRequestTransformer countToJobLaunchRequestTransformer() {
		return new CountToJobLaunchRequestTransformer(jobs, batchProperties.getJob().getName());
	}

	@Bean
	public JobLaunchingGateway jobLaunchingGateway() {
		return new JobLaunchingGateway(jobLauncher);
	}

	@Bean
	public MethodInvokingMessageSource countMessageSource() {
		MethodInvokingMessageSource messageSource = new MethodInvokingMessageSource();
		messageSource.setObject(new AtomicLong());
		messageSource.setMethodName("incrementAndGet");
		return messageSource;
	}

	@Bean
	public IntegrationFlow integrationFlow() {
		return IntegrationFlow
				.from(countMessageSource())
				.transform(countToJobLaunchRequestTransformer())
				.handle(jobLaunchingGateway())

				// なんか例外が出るようになったのでnullChannelを設定するようにした、、、
				// Caused by: org.springframework.messaging.core.DestinationResolutionException: no output-channel or replyChannel header available
				//		         at org.springframework.integration.handler.AbstractMessageProducingHandler.sendOutput(AbstractMessageProducingHandler.java:479)
				//		         at org.springframework.integration.handler.AbstractMessageProducingHandler.doProduceOutput(AbstractMessageProducingHandler.java:339)
				//		         at org.springframework.integration.handler.AbstractMessageProducingHandler.produceOutput(AbstractMessageProducingHandler.java:268)
				.channel("nullChannel")

				.log()
				.get();
	}
}
