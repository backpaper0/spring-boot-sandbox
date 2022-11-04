package com.example.chunk;

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
class ChunkExampleTest {

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private ChunkExample example;

	@Test
	void chunkExampleJob() throws Exception {
		final Job job = example.chunkExampleJob();
		final JobParameters jobParameters = new JobParameters();
		jobLauncher.run(job, jobParameters);
	}
}
