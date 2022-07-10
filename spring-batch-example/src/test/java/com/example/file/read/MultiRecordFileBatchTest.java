package com.example.file.read;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MultiRecordFileBatchTest {

	@Autowired
	JobLauncher jobLauncher;

	@Autowired
	MultiRecordFileBatch config;

	@Test
	void test() throws Exception {
		jobLauncher.run(config.multiRecordFileJob(), new JobParameters());

		List<?> items = config.multiRecordFileItemWriter().getWrittenItems();

		assertThat(items)
				.isEqualTo(List.of(
						new MultiRecordItem.Header("H", null),
						new MultiRecordItem.Data("D", "foo", 1),
						new MultiRecordItem.Data("D", "bar", 2),
						new MultiRecordItem.Data("D", "baz", 3),
						new MultiRecordItem.Footer("F", 3, null)));
	}
}
