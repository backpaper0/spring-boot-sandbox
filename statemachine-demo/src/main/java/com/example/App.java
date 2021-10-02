package com.example;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.config.StateMachineFactory;

import reactor.core.publisher.Mono;

@SpringBootApplication
public class App implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	private final StateMachineFactory<States, Events> stateMachineFactory;

	public App(StateMachineFactory<States, Events> stateMachineFactory) {
		this.stateMachineFactory = stateMachineFactory;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		{
			Message<Events> message = MessageBuilder.withPayload(Events.CREDIT_RESERVED).build();
			stateMachineFactory.getStateMachine("foo").sendEvent(Mono.just(message)).subscribe();
		}
		{
			Message<Events> message = MessageBuilder.withPayload(Events.FAILURE_CREDIT_RESERVATION).build();
			stateMachineFactory.getStateMachine("bar").sendEvent(Mono.just(message)).subscribe();
		}
	}
}
