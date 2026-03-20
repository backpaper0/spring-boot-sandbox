package com.example.tasklet;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TaskletDemoBatchTest {

    @Autowired
    JobOperator jobOperator;

    @Autowired
    TaskletDemoBatch config;

    @Test
    void test() throws Exception {
        jobOperator.run(config.taskletDemoJob(), new JobParameters());
    }
}
