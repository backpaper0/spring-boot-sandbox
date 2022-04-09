package study.provider;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.misc.BarImpl1;
import com.example.misc.Baz;
import com.example.misc.Foo;

@SpringBootTest
public class ObjectProviderTest {

	@Autowired
	private FooBarBaz fooBarBaz;

	@Test
	void foo() {
		assertEquals(new Foo(), fooBarBaz.getFoo().getIfAvailable());
	}

	@Test
	void bar1() {
		assertEquals(new BarImpl1(), fooBarBaz.getBar1().getIfAvailable());
	}

	@Test
	void bar2() {
		assertNull(fooBarBaz.getBar2().getIfAvailable());
	}

	@Test
	void baz() {
		assertEquals(new Baz("1"), fooBarBaz.getBaz().getIfAvailable());
	}

	@Test
	void baz1() {
		assertEquals(new Baz("1"), fooBarBaz.getBaz1().getIfAvailable());
	}

	@Test
	void baz2() {
		assertNull(fooBarBaz.getBaz2().getIfAvailable());
	}
}
