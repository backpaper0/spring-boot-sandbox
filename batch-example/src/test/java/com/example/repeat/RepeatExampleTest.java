package com.example.repeat;

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
public class RepeatExampleTest {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private RepeatExample example;

    @Test
    public void simpleRepeatJob() throws Exception {
        final Job job = example.simpleRepeatJob();
        final JobParameters jobParameters = new JobParameters();
        jobLauncher.run(job, jobParameters);
    }
}
