package com.example;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.integration.launch.JobLaunchRequest;
import org.springframework.integration.annotation.Transformer;

public class CountToJobLaunchRequestTransformer {

	private final Job job;

	public CountToJobLaunchRequestTransformer(Job job) {
		this.job = job;
	}

	@Transformer
	public JobLaunchRequest transform(long count) {
		JobParameters jobParameters = new JobParametersBuilder()
				.addLong("run.id", count)
				.toJobParameters();
		return new JobLaunchRequest(job, jobParameters);
	}
}
