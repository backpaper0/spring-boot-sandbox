package com.example.one_to_many;

import static java.util.stream.Collectors.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@SpringJUnitConfig
@SpringBootTest
@Transactional
class OneToManyTest {

	@Autowired
	private EntityManager em;

	private Long author1;
	private Long book1;
	private Long book2;
	private Long book3;

	@BeforeEach
	void setUp() throws Exception {

		final AuthorO2M a = new AuthorO2M();
		a.setName("コナン・ドイル");

		final BookO2M b1 = new BookO2M();
		b1.setTitle("緋色の研究");

		final BookO2M b2 = new BookO2M();
		b2.setTitle("四つの署名");

		final BookO2M b3 = new BookO2M();
		b3.setTitle("恐怖の谷");

		a.setBooks(Stream.of(b1, b2).collect(toSet()));

		em.persist(a);
		em.persist(b1);
		em.persist(b2);
		em.persist(b3);

		em.flush();

		author1 = a.getId();
		book1 = b1.getId();
		book2 = b2.getId();
		book3 = b3.getId();
	}

	@AfterEach
	void tearDown() throws Exception {
		em.remove(em.find(AuthorO2M.class, author1));
		em.remove(em.find(BookO2M.class, book1));
		em.remove(em.find(BookO2M.class, book2));
		em.remove(em.find(BookO2M.class, book3));
	}

	@Test
	void getBooksViaAuthor() throws Exception {
		final AuthorO2M a = em.find(AuthorO2M.class, author1);
		final Set<String> bs = a.getBooks().stream().map(BookO2M::getTitle).collect(toSet());
		assertEquals(Stream.of("緋色の研究", "四つの署名").collect(toSet()), bs);
	}
}
