package com.example.test.propertyoverridden;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PropertyOverriddenApp implements ApplicationRunner {

	public static void main(final String[] args) {
		final var app = new SpringApplication(PropertyOverriddenApp.class);
		app.setWebApplicationType(WebApplicationType.NONE);
		app.run(args);
	}

	@Value("${foo.bar.baz:Hello, world!}")
	private String prop;

	@Override
	public void run(final ApplicationArguments args) throws Exception {
		System.out.println(getProp());
	}

	public String getProp() {
		return prop;
	}
}
