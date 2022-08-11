
package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
//@EnableBatchProcessing
public class App {

	public static void main(String[] args) {

		@SuppressWarnings("resource")
		ConfigurableApplicationContext applicationContext = SpringApplication.run(App.class, args);

		int exitCode = SpringApplication.exit(applicationContext);

		System.exit(exitCode);
	}
}
