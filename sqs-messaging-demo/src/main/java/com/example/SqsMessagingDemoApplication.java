package com.example;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.amazonaws.services.sqs.AmazonSQSAsync;

import io.awspring.cloud.messaging.core.QueueMessageChannel;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;

@SpringBootApplication
@EnableScheduling
public class SqsMessagingDemoApplication {

	public static void main(String[] args) {
		System.setProperty("com.amazonaws.sdk.disableEc2Metadata", "true");
		ConfigurableApplicationContext applicationContext = SpringApplication
				.run(SqsMessagingDemoApplication.class, args);
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		SpringApplication.exit(applicationContext);
	}

	@Bean
	public QueueMessagingTemplate queueMessagingTemplate(AmazonSQSAsync amazonSqs) {
		QueueMessagingTemplate messagingTemplate = new QueueMessagingTemplate(amazonSqs);
		String queueUrl = "http://localhost:4566/000000000000/demo-queue";
		QueueMessageChannel defaultDestination = new QueueMessageChannel(amazonSqs, queueUrl);
		messagingTemplate.setDefaultDestination(defaultDestination);
		return messagingTemplate;
	}

	@Autowired
	private QueueMessagingTemplate messagingTemplate;

	@Scheduled(cron = "* * * * * *")
	public void sendMessages() {
		String message = "Hello World " + LocalDateTime.now();
		System.out.println("Send: " + message);
		messagingTemplate.convertAndSend("Hello World " + LocalDateTime.now());
	}

	@SqsListener("http://localhost:4566/000000000000/demo-queue")
	public void listen(String message) {
		System.out.println("Receive: " + message);
	}
}
