package study.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestExecutionListeners.MergeMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ExampleConfig.class)
@TestExecutionListeners(listeners = ExampleTestExecutionListener.class,
        mergeMode = MergeMode.MERGE_WITH_DEFAULTS)
public class TestExecutionListenerExample {

    @Autowired
    private Example example;

    @Test
    public void test() throws Exception {
        System.out.println(example.getValue());
    }
}
