package study.beanname;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import study.beanname.BeanNameExample2.FoobarContainer;

@SpringJUnitConfig(BeanNameExample2.class)
class BeanNameExample2Test {

	@Autowired
	private FoobarContainer container;

	@Test
	void test() {
		assertEquals("FirstFoobar", container.getFirstFoobar().name());
		assertEquals("SecondFoobar", container.getSecondFoobar().name());
		assertEquals("ThirdFoobar", container.getThirdFoobar().name());
	}
}
