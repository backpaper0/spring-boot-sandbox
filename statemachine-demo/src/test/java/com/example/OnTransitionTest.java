package com.example;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.EnumSet;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import com.example.OnTransitionTest.StateMachineConfig.Foobar;

import reactor.core.publisher.Mono;

@SpringBootTest
public class OnTransitionTest {

	@Autowired
	private StateMachine<States, Events> stateMachine;
	@Autowired
	private Foobar foobar;

	@Test
	void test() {
		stateMachine.startReactively().subscribe();
		assertEquals(States.SI, stateMachine.getState().getId());
		assertFalse(foobar.called1);
		assertFalse(foobar.called2);

		stateMachine.sendEvent(Mono.just(MessageBuilder.withPayload(Events.E1).build())).subscribe();
		assertEquals(States.S1, stateMachine.getState().getId());
		assertTrue(foobar.called1);
		assertFalse(foobar.called2);

		stateMachine.sendEvent(Mono.just(MessageBuilder.withPayload(Events.E2).build())).subscribe();
		assertEquals(States.S2, stateMachine.getState().getId());
		assertTrue(foobar.called1);
		assertTrue(foobar.called2);
	}

	enum States {
		SI, S1, S2
	}

	enum Events {
		E1, E2
	}

	@TestConfiguration
	@EnableStateMachine
	static class StateMachineConfig extends EnumStateMachineConfigurerAdapter<States, Events> {

		@Override
		public void configure(StateMachineConfigurationConfigurer<States, Events> config) throws Exception {
			config.withConfiguration()
					.autoStartup(false)
					.machineId("on-tranction-test");
		}

		@Override
		public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
			states.withStates()
					.states(EnumSet.allOf(States.class))
					.initial(States.SI)
					.end(States.S2);
		}

		@Override
		public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
			transitions
					.withExternal()
					.source(States.SI).target(States.S1).event(Events.E1)

					.and()
					.withExternal()
					.source(States.S1).target(States.S2).event(Events.E2);
		}

		@TestComponent
		@WithStateMachine(id = "on-tranction-test")
		static class Foobar {

			boolean called1;
			boolean called2;

			@OnTransition(source = "SI", target = "S1")
			public void call1() {
				called1 = true;
			}

			@StatesOnTransition(source = States.S1, target = States.S2)
			public void call2() {
				called2 = true;
			}
		}
	}

	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	@OnTransition
	@interface StatesOnTransition {

		States[] source() default {};

		States[] target() default {};
	}
}
