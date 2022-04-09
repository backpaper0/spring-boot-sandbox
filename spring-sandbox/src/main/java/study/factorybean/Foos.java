package study.factorybean;

import org.springframework.stereotype.Component;

import com.example.misc.Foo;

@Component
public class Foos {

	private final Foo foo1;
	private final Foo foo2;

	public Foos(Foo foo1, Foo foo2) {
		this.foo1 = foo1;
		this.foo2 = foo2;
	}

	public Foo getFoo1() {
		return foo1;
	}

	public Foo getFoo2() {
		return foo2;
	}
}
