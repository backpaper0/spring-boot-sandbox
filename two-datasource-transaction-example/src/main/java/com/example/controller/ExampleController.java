package com.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.ExampleService;

@RestController
public class ExampleController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final ExampleService service;

	public ExampleController(final ExampleService service) {
		this.service = service;
	}

	@GetMapping("/1")
	public String getFirst() {
		logger.info("begin getFirst");
		service.selectFirstDatabase();
		logger.info("end getFirst");
		return "OK";
	}

	@GetMapping("/2")
	public String getSecond() {
		logger.info("begin getSecond");
		service.selectSecondDatabase();
		logger.info("end getSecond");
		return "OK";
	}

	@GetMapping("/1t")
	public String getFirstWithTransaction() {
		logger.info("begin getFirstWithTransaction");
		service.selectFirstDatabaseWithTransaction();
		logger.info("end getFirstWithTransaction");
		return "OK";
	}

	@GetMapping("/2t")
	public String getSecondWithTransaction() {
		logger.info("begin getSecondWithTransaction");
		service.selectSecondDatabase();
		logger.info("end getSecondWithTransaction");
		return "OK";
	}
}
