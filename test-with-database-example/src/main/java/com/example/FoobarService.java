package com.example;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FoobarService {

	private final FoobarRepository repository;

	public FoobarService(FoobarRepository repository) {
		this.repository = repository;
	}

	@Transactional
	public Iterable<Foobar> findAll() {
		return repository.findAll();
	}
}
