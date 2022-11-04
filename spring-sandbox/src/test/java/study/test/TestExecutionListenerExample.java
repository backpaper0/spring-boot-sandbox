package study.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestExecutionListeners.MergeMode;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = ExampleConfig.class)
@TestExecutionListeners(listeners = ExampleTestExecutionListener.class,
		mergeMode = MergeMode.MERGE_WITH_DEFAULTS)
class TestExecutionListenerExample {

	@Autowired
	private Example example;

	@Test
	void test() throws Exception {
		System.out.println(example.getValue());
	}
}
