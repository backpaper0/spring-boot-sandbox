package com.example.parameter;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ParameterExampleTest {

    @Autowired
    JobOperator jobOperator;

    @Autowired
    ParameterExampleConfig config;

    @Test
    void test() throws Exception {
        String myParam = UUID.randomUUID().toString();
        JobParameters jobParameters =
                new JobParametersBuilder().addString("my.param", myParam).toJobParameters();
        jobOperator.run(config.parameterExampleJob(), jobParameters);
        assertEquals(myParam, config.parameterExampleTasklet().getValue());
    }
}
