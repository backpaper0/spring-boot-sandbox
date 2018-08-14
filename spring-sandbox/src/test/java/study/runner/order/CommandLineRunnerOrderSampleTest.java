package study.runner.order;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class CommandLineRunnerOrderSampleTest {

    @Autowired
    private ValuesHolder valuesHolder;

    @Test
    public void test() throws Exception {
        final List<String> values = valuesHolder.get();
        assertEquals(Arrays.asList("foo", "bar", "baz", "qux"), values);
    }
}
