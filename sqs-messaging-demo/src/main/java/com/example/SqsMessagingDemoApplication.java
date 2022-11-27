package com.example;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SqsMessagingDemoApplication {

	public static void main(String[] args) {
		//		System.setProperty("aws.disableEc2Metadata", "true");
		try (ConfigurableApplicationContext applicationContext = SpringApplication
				.run(SqsMessagingDemoApplication.class, args)) {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
