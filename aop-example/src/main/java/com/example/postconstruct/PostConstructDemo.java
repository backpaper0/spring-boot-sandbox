package com.example.postconstruct;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.example.Asterisk;

/**
 * PostConstructが付いたメソッドはAOPされないっぽい
 *
 */
@Component
@Asterisk
public class PostConstructDemo {

	private static final Logger logger = LoggerFactory.getLogger(PostConstructDemo.class);

	@PostConstruct
	public void init() {
		logger.info("PostConstructDemo#init");
	}

	public void run() {
		logger.info("PostConstructDemo#run");
	}
}
