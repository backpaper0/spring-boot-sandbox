package com.example.chunk;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class ChunkExampleTest {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private ChunkExample example;

    @Test
    public void chunkExampleJob() throws Exception {
        final Job job = example.chunkExampleJob();
        final JobParameters jobParameters = new JobParameters();
        jobLauncher.run(job, jobParameters);
    }
}
