package com.example.chunk;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class ChunkExampleTest {

    @Autowired
    private JobOperator jobOperator;

    @Autowired
    private ChunkExample example;

    @Test
    void chunkExampleJob() throws Exception {
        final Job job = example.chunkExampleJob();
        final JobParameters jobParameters = new JobParameters();
        jobOperator.run(job, jobParameters);
    }
}
