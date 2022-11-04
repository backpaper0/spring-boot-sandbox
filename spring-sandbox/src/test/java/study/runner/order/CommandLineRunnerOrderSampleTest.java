package study.runner.order;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CommandLineRunnerOrderSampleTest {

	@Autowired
	private ValuesHolder valuesHolder;

	@Test
	void test() throws Exception {
		final List<String> values = valuesHolder.get();
		assertEquals(Arrays.asList("foo", "bar", "baz", "qux"), values);
	}
}
