package study.test;

import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;

public class ExampleTestExecutionListener implements TestExecutionListener {

	@Override
	public void beforeTestExecution(final TestContext testContext) throws Exception {
		System.out.println("**** before ****");
	}

	@Override
	public void afterTestClass(final TestContext testContext) throws Exception {
		System.out.println("**** after ****");
	}
}
