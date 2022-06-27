package com.example.profile;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwitchListenerByProfileExample {

	@Autowired
	private SwitchListenerByProfileExampleListenerBuilder builder;
	@Autowired
	private StepBuilderFactory steps;
	@Autowired
	private JobBuilderFactory jobs;

	@Bean
	public Step switchListenerByProfileExampleStep() {
		return steps.get("SwitchListenerByProfileExample")
				.tasklet((contribution, chunkContext) -> {
					System.out.println("Tasklet#execute");
					return RepeatStatus.FINISHED;
				})
				.listener(builder.build())
				.build();
	}

	@Bean
	public Job switchListenerByProfileExampleJob() {
		return jobs.get("SwitchListenerByProfileExample")
				.start(switchListenerByProfileExampleStep())
				.build();
	}
}
