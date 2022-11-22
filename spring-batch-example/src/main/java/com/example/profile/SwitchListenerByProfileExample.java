package com.example.profile;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class SwitchListenerByProfileExample {

	@Autowired
	private JobRepository jobRepository;
	@Autowired
	private PlatformTransactionManager transactionManager;

	@Autowired
	private SwitchListenerByProfileExampleListenerBuilder builder;

	@Bean
	public Step switchListenerByProfileExampleStep() {
		return new StepBuilder("SwitchListenerByProfileExample", jobRepository)
				.tasklet((contribution, chunkContext) -> {
					System.out.println("Tasklet#execute");
					return RepeatStatus.FINISHED;
				}, transactionManager)
				.listener(builder.build())
				.build();
	}

	@Bean
	public Job switchListenerByProfileExampleJob() {
		return new JobBuilder("SwitchListenerByProfileExample", jobRepository)
				.start(switchListenerByProfileExampleStep())
				.build();
	}
}
