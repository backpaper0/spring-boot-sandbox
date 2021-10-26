package com.example;

import static org.junit.jupiter.api.Assertions.*;

import java.util.EnumSet;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import reactor.core.publisher.Mono;

@SpringBootTest
public class ChoiceTest {

	@Autowired
	private StateMachine<States, Events> stateMachine;

	private static States state;

	@Test
	void testFirst() {
		state = States.S2;
		stateMachine.sendEvent(Mono.just(MessageBuilder.withPayload(Events.E1).build())).subscribe();
		assertEquals(States.S2, stateMachine.getState().getId());
	}

	@Test
	void testThen() {
		state = States.S3;
		stateMachine.sendEvent(Mono.just(MessageBuilder.withPayload(Events.E1).build())).subscribe();
		assertEquals(States.S3, stateMachine.getState().getId());
	}

	@Test
	void testLast() {
		state = null;
		stateMachine.sendEvent(Mono.just(MessageBuilder.withPayload(Events.E1).build())).subscribe();
		assertEquals(States.S4, stateMachine.getState().getId());
	}

	@BeforeEach
	void start() {
		stateMachine.startReactively().subscribe();
	}

	@AfterEach
	void stop() {
		stateMachine.stopReactively().subscribe();
	}

	public enum States {
		SI, S1, S2, S3, S4
	}

	public enum Events {
		E1, E2
	}

	@TestConfiguration
	@EnableStateMachine
	public static class StateMachineConfig extends EnumStateMachineConfigurerAdapter<States, Events> {

		@Override
		public void configure(StateMachineConfigurationConfigurer<States, Events> config)
				throws Exception {
			config
					.withConfiguration()
					.autoStartup(false)
					.listener(listener());
		}

		@Override
		public void configure(StateMachineStateConfigurer<States, Events> states)
				throws Exception {
			states
					.withStates()
					.initial(States.SI)
					.choice(States.S1)
					.states(EnumSet.allOf(States.class));
		}

		@Override
		public void configure(StateMachineTransitionConfigurer<States, Events> transitions)
				throws Exception {
			transitions
					.withExternal()
					.source(States.SI).target(States.S1).event(Events.E1)
					.and()
					.withChoice()
					.source(States.S1).first(States.S2, context -> ChoiceTest.state == States.S2)
					.then(States.S3, context -> ChoiceTest.state == States.S3)
					.last(States.S4);
		}

		@Bean
		public StateMachineListener<States, Events> listener() {
			return new StateMachineListenerAdapter<>() {

				@Override
				public void stateMachineStarted(StateMachine<States, Events> stateMachine) {
					System.out.println("StateMachine started");
				}

				@Override
				public void stateChanged(State<States, Events> from, State<States, Events> to) {
					System.out.println("State change to " + to.getId());
				}
			};
		}
	}
}
