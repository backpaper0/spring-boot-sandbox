package com.example.notgood.staticx;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.example.notgood.staticx.cantchanged.CanNotChangedObject;

@Configuration
@ComponentScan(basePackages = "com.example.notgood.staticx.component")
public class StaticExample {

	public static void main(final String[] args) {
		try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
			context.register(StaticExample.class);
			context.refresh();

			final CanNotChangedObject obj = new CanNotChangedObject();
			obj.run(System.out);
		}
	}
}
