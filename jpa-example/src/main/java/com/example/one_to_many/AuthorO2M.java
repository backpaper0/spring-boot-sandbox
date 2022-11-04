package com.example.one_to_many;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class AuthorO2M {

	@Id
	@GeneratedValue
	private Long id;
	private String name;

	@OneToMany
	private Set<BookO2M> books;

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Set<BookO2M> getBooks() {
		return books;
	}

	public void setBooks(final Set<BookO2M> books) {
		this.books = books;
	}
}
