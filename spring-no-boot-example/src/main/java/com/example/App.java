package com.example;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.example.component.Bar;
import com.example.component.Baz;
import com.example.component.Foo;

@ComponentScan
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class App {

	public static void main(String[] args) {
		var applicationContext = new AnnotationConfigApplicationContext(App.class);
		try (applicationContext) {

			var foo = applicationContext.getBean(Foo.class);
			System.out.println(foo.name());

			var bar = applicationContext.getBean(Bar.class);
			System.out.println(bar.name());

			var baz = applicationContext.getBean(Baz.class);
			System.out.println(baz.name());
		}
	}
}
