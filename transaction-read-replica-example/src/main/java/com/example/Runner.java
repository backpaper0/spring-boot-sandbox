package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.example.service.ExampleService;

@Component
public class Runner implements ApplicationRunner {

	private static final Logger logger = LoggerFactory.getLogger(Runner.class);
	private final ExampleService service;

	public Runner(ExampleService service) {
		this.service = service;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		logger.info("{}", service.findAll());
		logger.info("{}", service.findAllFromReadReplica());
		logger.info("{}", service.findAllFromReadReplica2());
	}
}
