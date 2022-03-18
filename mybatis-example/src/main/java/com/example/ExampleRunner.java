package com.example;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ExampleRunner implements ApplicationRunner {

	private final MessageMapper mapper;

	@Override
	@Transactional
	public void run(ApplicationArguments args) throws Exception {
		System.out.println("Mapper = " + mapper.getClass());
		System.out.println();

		System.out.println("[selectAll1]");
		for (Message message : mapper.selectAll1()) {
			System.out.println(message);
		}
		System.out.println();

		System.out.println("[selectAll2]");
		for (Message message : mapper.selectAll2()) {
			System.out.println(message);
		}
		System.out.println();
	}
}
