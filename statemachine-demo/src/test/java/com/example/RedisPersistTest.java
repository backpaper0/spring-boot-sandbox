package com.example;

import static org.junit.jupiter.api.Assertions.*;

import java.util.EnumSet;
import java.util.UUID;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.data.redis.RedisStateMachineContextRepository;
import org.springframework.statemachine.data.redis.RedisStateMachinePersister;
import org.springframework.statemachine.persist.RepositoryStateMachinePersist;

import reactor.core.publisher.Mono;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class RedisPersistTest {

	@Autowired
	private StateMachineFactory<States, Events> smf;
	@Autowired
	private RedisStateMachinePersister<States, Events> persister;

	private final String id = UUID.randomUUID().toString();

	@Test
	@Order(100)
	void test1() throws Exception {
		StateMachine<States, Events> sm = smf.getStateMachine();
		assertNull(sm.getState());

		sm.startReactively().subscribe();
		assertEquals(States.S1, sm.getState().getId());

		sm.sendEvent(Mono.just(MessageBuilder.withPayload(Events.E1).build())).subscribe();
		assertEquals(States.S2, sm.getState().getId());

		persister.persist(sm, id);
	}

	@Test
	@Order(200)
	void test2() throws Exception {
		StateMachine<States, Events> sm = smf.getStateMachine();

		sm = persister.restore(sm, id);
		assertEquals(States.S2, sm.getState().getId());

		sm.sendEvent(Mono.just(MessageBuilder.withPayload(Events.E2).build())).subscribe();
		assertEquals(States.S3, sm.getState().getId());
	}

	enum States {
		S1, S2, S3
	}

	enum Events {
		E1, E2
	}

	@TestConfiguration
	@EnableStateMachineFactory
	static class StateMachineConfig extends EnumStateMachineConfigurerAdapter<States, Events> {

		@Override
		public void configure(StateMachineConfigurationConfigurer<States, Events> config) throws Exception {
			config.withConfiguration()
					.autoStartup(false);
		}

		@Override
		public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
			states.withStates()
					.initial(States.S1)
					.end(States.S3)
					.states(EnumSet.allOf(States.class));
		}

		@Override
		public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
			transitions
					.withExternal()
					.source(States.S1).target(States.S2).event(Events.E1)

					.and().withExternal()
					.source(States.S2).target(States.S3).event(Events.E2);
		}

		@Bean
		public RedisStateMachineContextRepository<States, Events> stateMachineContextRepository(
				RedisConnectionFactory connectionFactory) {
			return new RedisStateMachineContextRepository<>(connectionFactory);
		}

		@Bean
		public RepositoryStateMachinePersist<States, Events> stateMachinePersist(
				RedisStateMachineContextRepository<States, Events> repository) {
			return new RepositoryStateMachinePersist<>(repository);
		}

		@Bean
		public RedisStateMachinePersister<States, Events> stateMachinePersister(
				RepositoryStateMachinePersist<States, Events> stateMachinePersist) {
			return new RedisStateMachinePersister<>(stateMachinePersist);
		}
	}
}
