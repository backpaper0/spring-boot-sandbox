package com.example.file.read;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class NoLineBreaksFlatFileExampleTest {

    @Autowired
    JobOperator jobOperator;

    @Autowired
    NoLineBreaksFlatFileExample config;

    @Test
    void test() throws Exception {
        jobOperator.run(config.noLineBreaksFlatFileExampleJob(), new JobParameters());

        List<? extends ExampleItem> items =
                config.noLineBreaksFlatFileExampleWriter().getWrittenItems();

        assertEquals(
                List.of(
                        new ExampleItem(1, "foo"),
                        new ExampleItem(2, "bar"),
                        new ExampleItem(3, "baz"),
                        new ExampleItem(4, "qux")),
                items);
    }
}
