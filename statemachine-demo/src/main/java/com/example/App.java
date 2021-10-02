package com.example;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;

import reactor.core.publisher.Mono;

@SpringBootApplication
public class App implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	private final StateMachine<States, Events> stateMachine;

	public App(StateMachine<States, Events> stateMachine) {
		this.stateMachine = stateMachine;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		Message<Events> message = MessageBuilder.withPayload(Events.CREDIT_RESERVED).build();
		stateMachine.sendEvent(Mono.just(message)).subscribe();
	}
}
