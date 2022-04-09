package study.factorybean;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FactoryBeanTest {

	@Autowired
	private Foos foos;

	@Test
	void test() {
		assertEquals("Foo(1)", foos.getFoo1().toString());
		assertEquals("Foo(1)", foos.getFoo2().toString());
	}
}
