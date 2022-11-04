package study.beanname;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class BeanNameExample {

	//何も指定されなければメソッド名がbean nameになる。
	@Bean
	public Baz baz1() {
		return new Baz();
	}

	//@Beanのname要素が設定されていれば、それがbean nameになる。
	@Bean(name = "bazbaz")
	public Baz bar2() {
		return new Baz();
	}

	//FQCNがbean nameになる。
	@Component
	public static class Foo {
	}

	@Component("barbar")
	public static class Bar {
	}

	public static class Baz {
	}
}
