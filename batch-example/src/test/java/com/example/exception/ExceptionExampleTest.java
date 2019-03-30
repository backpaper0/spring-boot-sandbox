package com.example.exception;

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
class ExceptionExampleTest {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private ExceptionExample example;

    @Test
    void exceptionExampleJob1() throws Exception {
        final Job job = example.exceptionExampleJob1();
        final JobParameters jobParameters = new JobParameters();
        jobLauncher.run(job, jobParameters);
    }

    @Test
    void exceptionExampleJob2() throws Exception {
        final Job job = example.exceptionExampleJob2();
        final JobParameters jobParameters = new JobParameters();
        jobLauncher.run(job, jobParameters);
    }

    @Test
    void exceptionExampleJob3() throws Exception {
        final Job job = example.exceptionExampleJob3();
        final JobParameters jobParameters = new JobParameters();
        jobLauncher.run(job, jobParameters);
    }

    @Test
    void exceptionExampleJob4() throws Exception {
        final Job job = example.exceptionExampleJob4();
        final JobParameters jobParameters = new JobParameters();
        jobLauncher.run(job, jobParameters);
    }
}
