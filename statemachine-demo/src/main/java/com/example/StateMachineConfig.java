package com.example;

import java.util.EnumSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

@Configuration
@EnableStateMachineFactory
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<States, Events> {

	private static final Logger logger = LoggerFactory.getLogger(StateMachineConfig.class);

	@Override
	public void configure(StateMachineConfigurationConfigurer<States, Events> config) throws Exception {
		StateMachineListener<States, Events> listener = new StateMachineListenerAdapter<>() {
			@Override
			public void stateChanged(State<States, Events> from, State<States, Events> to) {
				if (from != null) {
					logger.info("State change from {} to {}", from.getId(), to.getId());
				} else {
					logger.info("Initial state {}", to.getId());
				}
			}
		};
		config.withConfiguration().autoStartup(true).listener(listener);
	}

	@Override
	public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
		states.withStates()
				.states(EnumSet.allOf(States.class))
				.initial(States.PENDING, this::initial);
	}

	@Override
	public void configure(
			StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
		transitions
				.withExternal()
				.source(States.PENDING)
				.target(States.APPROVED)
				.event(Events.CREDIT_RESERVED)
				.action(this::approved)

				.and()
				.withExternal()
				.source(States.PENDING)
				.target(States.REJECTED)
				.event(Events.FAILURE_CREDIT_RESERVATION)
				.action(this::rejected);
	}

	private void initial(StateContext<States, Events> context) {
		logger.info("initial action");
	}

	private void approved(StateContext<States, Events> context) {
		logger.info("approved action");
	}

	private void rejected(StateContext<States, Events> context) {
		logger.info("rejected action");
	}
}
