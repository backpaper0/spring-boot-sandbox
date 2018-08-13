package com.example;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = ExampleConfig.class)
class ExampleConfigTest {

    @Autowired
    private ExampleConfig config;
    @Autowired
    private JobLauncher launcher;

    @Test
    void test() throws Exception {
        final Job job = config.job();
        final JobParameters jobParameters = new JobParameters();
        final JobExecution execution = launcher.run(job, jobParameters);
        while (execution.getStatus().isRunning()) {
            TimeUnit.MILLISECONDS.sleep(10L);
        }
    }
}
