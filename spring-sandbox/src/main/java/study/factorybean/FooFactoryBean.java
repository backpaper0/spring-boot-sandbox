package study.factorybean;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

import com.example.misc.Foo;

@Component
public class FooFactoryBean implements FactoryBean<Foo> {

	private final AtomicInteger index = new AtomicInteger();

	@Override
	public Foo getObject() {
		return new Foo(String.valueOf(index.incrementAndGet()));
	}

	@Override
	public Class<?> getObjectType() {
		return Foo.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
}
