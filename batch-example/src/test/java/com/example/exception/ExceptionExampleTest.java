package com.example.exception;

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
public class ExceptionExampleTest {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private ExceptionExample example;

    @Test
    public void exceptionExampleJob1() throws Exception {
        final Job job = example.exceptionExampleJob1();
        final JobParameters jobParameters = new JobParameters();
        jobLauncher.run(job, jobParameters);
    }

    @Test
    public void exceptionExampleJob2() throws Exception {
        final Job job = example.exceptionExampleJob2();
        final JobParameters jobParameters = new JobParameters();
        jobLauncher.run(job, jobParameters);
    }

    @Test
    public void exceptionExampleJob3() throws Exception {
        final Job job = example.exceptionExampleJob3();
        final JobParameters jobParameters = new JobParameters();
        jobLauncher.run(job, jobParameters);
    }

    @Test
    public void exceptionExampleJob4() throws Exception {
        final Job job = example.exceptionExampleJob4();
        final JobParameters jobParameters = new JobParameters();
        jobLauncher.run(job, jobParameters);
    }
}
