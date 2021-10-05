package com.example;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.cloud.sleuth.instrument.messaging.MessagingSleuthOperators;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

@Configuration
public class StreamFunctions {

	//	@Bean
	//	Supplier<String> uuid1() {
	//		Logger logger = LoggerFactory.getLogger("Example-1");
	//		return () -> {
	//			String s = UUID.randomUUID().toString();
	//			logger.info("uuid1: {}", s);
	//			return s;
	//		};
	//	}
	//
	//	@Bean
	//	Function<String, String> uppercase1(BeanFactory beanFactory) {
	//		Logger logger = LoggerFactory.getLogger("Example-1");
	//		return s -> {
	//			String t = s.toUpperCase();
	//			logger.info("uppercase1: {} -> {}", s, t);
	//			return t;
	//		};
	//	}
	//
	//	@Bean
	//	Consumer<String> logging1(BeanFactory beanFactory) {
	//		Logger logger = LoggerFactory.getLogger("Example-1");
	//		return s -> {
	//			logger.info("logging1: {}", s);
	//		};
	//	}

	@Bean
	Supplier<Message<String>> uuid2() {
		Logger logger = LoggerFactory.getLogger("Example-2");
		return () -> {
			String s = UUID.randomUUID().toString();
			logger.info("uuid2: {}", s);
			return MessageBuilder.withPayload(s).build();
		};
	}

	@Bean
	Function<Message<String>, Message<String>> uppercase2(BeanFactory beanFactory) {
		Logger logger = LoggerFactory.getLogger("Example-2");
		return m -> {
			MessagingSleuthOperators.spanFromMessage(beanFactory, m).name("uppercase2");
			String s = m.getPayload();
			String t = s.toUpperCase();
			logger.info("uppercase2: {} -> {}", s, t);
			return MessageBuilder.withPayload(t).build();
		};
	}

	@Bean
	Consumer<Message<String>> logging2(BeanFactory beanFactory) {
		Logger logger = LoggerFactory.getLogger("Example-2");
		return m -> {
			MessagingSleuthOperators.spanFromMessage(beanFactory, m).name("logging2");
			String s = m.getPayload();
			logger.info("logging2: {}", s);
		};
	}

	//	@Bean
	//	FunctionMessageSpanCustomizer myFunctionMessageSpanCustomizer() {
	//		return new FunctionMessageSpanCustomizer() {
	//			@Override
	//			public void customizeFunctionSpan(Span span, Message<?> message) {
	//			}
	//
	//			@Override
	//			public void customizeInputMessageSpan(Span span, Message<?> message) {
	//			}
	//
	//			@Override
	//			public void customizeOutputMessageSpan(Span span, Message<?> message) {
	//			}
	//		};
	//	}
}
