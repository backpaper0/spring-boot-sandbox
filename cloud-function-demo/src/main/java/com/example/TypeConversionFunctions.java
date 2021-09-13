package com.example;

import java.util.UUID;
import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class TypeConversionFunctions {

	@Bean
	public Function<NewPersonRequest, Person> person() {
		return request -> new Person(UUID.randomUUID(), request.getName());
	}

	public static class NewPersonRequest {

		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	public static class Person {

		private final UUID id;
		private final String name;

		public Person(UUID id, String name) {
			this.id = id;
			this.name = name;
		}

		public UUID getId() {
			return id;
		}

		public String getName() {
			return name;
		}
	}
}
