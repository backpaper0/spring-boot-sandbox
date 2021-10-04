package com.example;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StreamFunctions {

	private static final Logger logger = LoggerFactory.getLogger(StreamFunctions.class);

	@Bean
	Supplier<String> uuid() {
		return () -> {
			String s = UUID.randomUUID().toString();
			logger.info("uuid: {}", s);
			return s;
		};
	}

	@Bean
	Function<String, String> uppercase() {
		return s -> {
			String t = s.toUpperCase();
			logger.info("uppercase: {} -> {}", s, t);
			return t;
		};
	}

	@Bean
	Consumer<String> logging() {
		return s -> {
			logger.info("logging: {}", s);
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
