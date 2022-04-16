package com.example;

import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.integration.launch.JobLaunchRequest;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.integration.annotation.Transformer;
import org.springframework.util.PatternMatchUtils;

public class CountToJobLaunchRequestTransformer implements InitializingBean {

	private final List<Job> jobs;
	private final String jobNames;
	private Job job;

	public CountToJobLaunchRequestTransformer(List<Job> jobs, String jobNames) {
		this.jobs = jobs;
		this.jobNames = jobNames;
	}

	@Override
	public void afterPropertiesSet() {
		String[] jobsToRun = this.jobNames.split(",");
		for (Job job : this.jobs) {
			if (PatternMatchUtils.simpleMatch(jobsToRun, job.getName())) {
				this.job = job;
				return;
			}
		}
		throw new IllegalArgumentException();
	}

	@Transformer
	public JobLaunchRequest transform(long count) {
		JobParameters jobParameters = new JobParametersBuilder()
				.addLong("run.id", count)
				.toJobParameters();
		return new JobLaunchRequest(job, jobParameters);
	}
}
