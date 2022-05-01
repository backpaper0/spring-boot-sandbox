package com.example;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class CharReplacementExample implements ApplicationRunner {

	private final CharReplacementProcessor processor;

	public CharReplacementExample(CharReplacementProcessor processor) {
		this.processor = processor;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		String input = Files.readString(Path.of("input.txt"), StandardCharsets.UTF_8);
		String output = processor.process(input);
		System.out.println(output);
	}
}
