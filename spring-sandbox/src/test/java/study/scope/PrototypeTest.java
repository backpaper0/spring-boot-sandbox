package study.scope;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

class PrototypeTest {

	@Test
	void test() throws Exception {
		try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
			context.register(Foo.class);
			context.register(Bar.class);
			context.register(Hoge.class);
			context.refresh();

			final Bar bar = context.getBean(Bar.class);
			final Hoge hoge = context.getBean(Hoge.class);
			assertFalse(bar.getValue().equals(hoge.getValue()));
		}
	}

	@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	static class Foo {

		private final String value = UUID.randomUUID().toString();

		public String getValue() {
			return value;
		}
	}

	static class Bar {

		private final Foo foo;

		public Bar(final Foo foo) {
			this.foo = foo;
		}

		public String getValue() {
			return foo.getValue();
		}
	}

	static class Hoge {

		private final Foo foo;

		public Hoge(final Foo foo) {
			this.foo = foo;
		}

		public String getValue() {
			return foo.getValue();
		}
	}
}
