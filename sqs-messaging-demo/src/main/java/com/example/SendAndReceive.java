package com.example;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import io.awspring.cloud.sqs.annotation.SqsListener;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

@Component
public class SendAndReceive {

	@Autowired
	private SqsAsyncClient sqsAsyncClient;

	@Scheduled(cron = "* * * * * *")
	public void sendMessages() {
		String message = "Hello World " + LocalDateTime.now();
		System.out.println("Send: " + message);

		sqsAsyncClient.sendMessage(builder -> builder
				.queueUrl("http://localhost:4566/000000000000/demo-queue")
				.messageBody(message)
				.build());
	}

	@SqsListener("http://localhost:4566/000000000000/demo-queue")
	public void listen(String message) {
		System.out.println("Receive: " + message);
	}
}
