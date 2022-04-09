package study.beanpostprocessor;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import com.example.misc.Foo;

@Component
public class FooPostProcessor implements BeanPostProcessor {

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) {
		if (bean instanceof Foo) {
			return new FooWrapper((Foo) bean, "Before");
		}
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) {
		if (bean instanceof Foo) {
			return new FooWrapper((Foo) bean, "After");
		}
		return bean;
	}

	private static class FooWrapper extends Foo {

		private final Foo foo;
		private final String added;

		public FooWrapper(Foo foo, String added) {
			this.foo = foo;
			this.added = added;
		}

		@Override
		public String toString() {
			return added + "(" + foo.toString() + ")";
		}
	}
}
