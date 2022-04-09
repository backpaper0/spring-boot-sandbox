package study.customautowire;

import java.util.Collections;

import org.springframework.beans.factory.annotation.CustomAutowireConfigurer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.misc.Foo;

@SpringBootApplication
public class App {

	@Bean
	public CustomAutowireConfigurer customAutowireConfigurer() {
		CustomAutowireConfigurer configurer = new CustomAutowireConfigurer();
		configurer.setCustomQualifierTypes(Collections.singleton(MyQualifier.class));
		return configurer;
	}

	@Bean
	public Foo foo1() {
		return new Foo("1");
	}

	@Bean
	@MyQualifier
	public Foo foo2() {
		return new Foo("2");
	}
}
