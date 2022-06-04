package com.example.parameter;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ParameterExampleTest {

	@Autowired
	JobLauncher jobLauncher;

	@Autowired
	ParameterExampleConfig config;

	@Test
	void test() throws Exception {
		String myParam = UUID.randomUUID().toString();
		JobParameters jobParameters = new JobParametersBuilder()
				.addString("my.param", myParam)
				.toJobParameters();
		jobLauncher.run(config.parameterExampleJob(), jobParameters);
		assertEquals(myParam, config.parameterExampleTasklet().getValue());
	}
}
