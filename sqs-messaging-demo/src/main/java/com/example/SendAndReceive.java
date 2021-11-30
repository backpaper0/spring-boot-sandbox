package com.example;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;

@Component
public class SendAndReceive {

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
