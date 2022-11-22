package com.example.exception;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class ExceptionExampleTest {

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	@Qualifier("job1")
	private Job job1;

	@Autowired
	@Qualifier("job2")
	private Job job2;

	@Autowired
	@Qualifier("job3")
	private Job job3;

	@Autowired
	@Qualifier("job4")
	private Job job4;

	@Test
	void exceptionExampleJob1() throws Exception {
		JobParameters jobParameters = new JobParameters();
		jobLauncher.run(job1, jobParameters);
	}

	@Test
	void exceptionExampleJob2() throws Exception {
		JobParameters jobParameters = new JobParameters();
		jobLauncher.run(job2, jobParameters);
	}

	@Test
	void exceptionExampleJob3() throws Exception {
		JobParameters jobParameters = new JobParameters();
		jobLauncher.run(job3, jobParameters);
	}

	@Test
	void exceptionExampleJob4() throws Exception {
		JobParameters jobParameters = new JobParameters();
		jobLauncher.run(job4, jobParameters);
	}
}
