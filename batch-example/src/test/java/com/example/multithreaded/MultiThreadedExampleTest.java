package com.example.multithreaded;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class MultiThreadedExampleTest {

    @Autowired
    private JobOperator jobOperator;

    @Autowired
    private Job job;

    @Test
    void multiThreadedExampleJob() throws Exception {
        JobParameters jobParameters = new JobParameters();
        jobOperator.run(job, jobParameters);
    }
}
