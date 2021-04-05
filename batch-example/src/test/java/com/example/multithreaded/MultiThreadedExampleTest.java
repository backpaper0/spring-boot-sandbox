package com.example.multithreaded;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class MultiThreadedExampleTest {

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private MultiThreadedExample example;

	@Test
	void multiThreadedExampleJob() throws Exception {
		Job job = example.multiThreadedJob();
		JobParameters jobParameters = new JobParameters();
		jobLauncher.run(job, jobParameters);
	}
}
