package com.example;

import java.util.Objects;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;

@Service
public class MessageService {

	private final EntityManager em;

	public MessageService(final EntityManager em) {
		this.em = Objects.requireNonNull(em);
	}

	@Transactional
	public void setUpData() {
		em.persist(new Message("foo"));
		em.persist(new Message("bar"));
		em.persist(new Message("baz"));
		em.persist(new Message("qux"));
	}

	@Transactional(readOnly = true)
	public void printData() {
		try (Stream<Message> stream = em.createNamedQuery("selectAll", Message.class)
				.getResultStream()) {
			stream.forEach(System.out::println);
		}
	}
}
