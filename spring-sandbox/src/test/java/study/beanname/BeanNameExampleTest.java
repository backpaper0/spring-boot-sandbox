package study.beanname;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import study.beanname.BeanNameExample.Bar;
import study.beanname.BeanNameExample.Baz;
import study.beanname.BeanNameExample.Foo;

@SpringJUnitConfig(BeanNameExample.class)
class BeanNameExampleTest {

	@Autowired
	private ApplicationContext context;

	@Test
	void component() {
		final String[] actual = context.getBeanNamesForType(Foo.class);
		final String[] expected = new String[] {
				"study.beanname.BeanNameExample$Foo"
		};
		assertArrayEquals(expected, actual);
	}

	@Test
	void componentValueElement() {
		final String[] actual = context.getBeanNamesForType(Bar.class);
		final String[] expected = new String[] {
				"barbar"
		};
		assertArrayEquals(expected, actual);
	}

	@Test
	void beanAnnotation() {
		final String[] actual = context.getBeanNamesForType(Baz.class);
		final String[] expected = new String[] {
				"baz1",
				"bazbaz"
		};
		assertArrayEquals(expected, actual);
	}
}
