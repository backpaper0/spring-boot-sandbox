package com.example.tasklet;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TaskletDemoBatchTest {

	@Autowired
	JobLauncher jobLauncher;

	@Autowired
	TaskletDemoBatch config;

	@Test
	void test() throws Exception {
		jobLauncher.run(config.taskletDemoJob(), new JobParameters());
	}
}
