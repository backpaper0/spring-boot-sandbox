package study.runner.order;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CommandLineRunnerOrderSampleTest {

    @Autowired
    private ValuesHolder valuesHolder;

    @Test
    public void test() throws Exception {
        final List<String> values = valuesHolder.get();
        assertThat(values, is(Arrays.asList("foo", "bar", "baz", "qux")));
    }
}
