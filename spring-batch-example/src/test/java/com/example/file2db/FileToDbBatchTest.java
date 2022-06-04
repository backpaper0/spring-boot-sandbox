package com.example.file2db;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.junit5.api.DBRider;

@SpringBootTest
@DBRider
public class FileToDbBatchTest {

	@Autowired
	JobLauncher jobLauncher;

	@Autowired
	FileToDbBatch config;

	@Test
	@DataSet("FileToDb/dataset/demo1.csv")
	@ExpectedDataSet("FileToDb/expected/demo1.csv")
	void test() throws Exception {
		JobParameters jobParameters = new JobParametersBuilder()
				.addString("input.file", "inputs/input-valid.csv")
				.toJobParameters();

		jobLauncher.run(config.fileToDbJob(), jobParameters);
	}
}
