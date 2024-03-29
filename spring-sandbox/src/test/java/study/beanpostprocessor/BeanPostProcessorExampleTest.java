package study.beanpostprocessor;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.misc.Baz;

@SpringBootTest
public class BeanPostProcessorExampleTest {

	@Autowired
	private Baz baz;

	@Test
	void test() {
		assertEquals("Baz(After(Before(Foo())))", baz.toString());
	}
}
