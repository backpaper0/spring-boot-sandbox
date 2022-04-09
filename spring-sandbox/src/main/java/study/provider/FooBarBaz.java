package study.provider;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.example.misc.BarImpl1;
import com.example.misc.BarImpl2;
import com.example.misc.Baz;
import com.example.misc.Foo;

@Component
public class FooBarBaz {

	private final ObjectProvider<Foo> foo;
	private final ObjectProvider<BarImpl1> bar1;
	private final ObjectProvider<BarImpl2> bar2;
	private final ObjectProvider<Baz> baz;
	private final ObjectProvider<Baz> baz1;
	private final ObjectProvider<Baz> baz2;

	public FooBarBaz(
			ObjectProvider<Foo> foo,
			ObjectProvider<BarImpl1> bar1,
			ObjectProvider<BarImpl2> bar2,
			ObjectProvider<Baz> baz,
			@Qualifier("baz1") ObjectProvider<Baz> baz1,
			@Qualifier("baz2") ObjectProvider<Baz> baz2) {
		this.foo = foo;
		this.bar1 = bar1;
		this.bar2 = bar2;
		this.baz = baz;
		this.baz1 = baz1;
		this.baz2 = baz2;
	}

	public ObjectProvider<Foo> getFoo() {
		return foo;
	}

	public ObjectProvider<BarImpl1> getBar1() {
		return bar1;
	}

	public ObjectProvider<BarImpl2> getBar2() {
		return bar2;
	}

	public ObjectProvider<Baz> getBaz() {
		return baz;
	}

	public ObjectProvider<Baz> getBaz1() {
		return baz1;
	}

	public ObjectProvider<Baz> getBaz2() {
		return baz2;
	}
}
