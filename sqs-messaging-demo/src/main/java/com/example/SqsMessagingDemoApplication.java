package com.example;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.amazonaws.services.sqs.AmazonSQSAsync;

import io.awspring.cloud.messaging.core.QueueMessageChannel;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;

@SpringBootApplication
@EnableScheduling
public class SqsMessagingDemoApplication {

	public static void main(String[] args) {
		System.setProperty("com.amazonaws.sdk.disableEc2Metadata", "true");
		try (ConfigurableApplicationContext applicationContext = SpringApplication
				.run(SqsMessagingDemoApplication.class, args)) {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Bean
	public QueueMessagingTemplate queueMessagingTemplate(AmazonSQSAsync amazonSqs) {
		QueueMessagingTemplate messagingTemplate = new QueueMessagingTemplate(amazonSqs);
		String queueUrl = "http://localhost:4566/000000000000/demo-queue";
		QueueMessageChannel defaultDestination = new QueueMessageChannel(amazonSqs, queueUrl);
		messagingTemplate.setDefaultDestination(defaultDestination);
		return messagingTemplate;
	}
}
