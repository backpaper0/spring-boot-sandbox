package com.example.stream;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class StreamExampleTest {

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job job;

	@Test
	void streamExampleJob() throws Exception {
		JobParameters jobParameters = new JobParameters();
		jobLauncher.run(job, jobParameters);
	}
}
