package study.proxy;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.misc.Foo;

@SpringBootTest
public class ProxyTest {

	@Autowired
	private FooService fooService;

	@Test
	void test() {
		var foo = fooService.getFoo1();
		assertEquals("Foo(a)", foo.toString());
		assertEquals(Foo.class.getName(), foo.getClass().getName());
	}

	@Test
	void testProxy() {
		var foo = fooService.getFoo2();
		assertEquals("Foo(b)", foo.toString());
		assertNotEquals(Foo.class.getName(), foo.getClass().getName());
	}
}
