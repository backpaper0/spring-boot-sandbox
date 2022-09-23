package com.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import com.example.entity.Singer;
import com.example.entity.Song;
import com.example.repository.SingerRepository;
import com.example.repository.SongRepository;

@SpringBootTest
public class SpringDataJdbcTest {

	@Autowired
	private SingerRepository singers;
	@Autowired
	private SongRepository songs;

	@Test
	void singer1() {
		Singer singer = singers.findById(1).get();
		assertAll(
				() -> assertEquals(1, singer.getId()),
				() -> assertEquals("LUNA SEA", singer.getName()),
				() -> assertEquals(1, singer.getVersion()),
				() -> assertEquals(3, singer.getSongs().size()));
	}

	@Test
	void singer2() {
		Singer singer = singers.findById(2).get();
		assertAll(
				() -> assertEquals(2, singer.getId()),
				() -> assertEquals("THEE MICHELLE GUN ELEPHANT", singer.getName()),
				() -> assertEquals(1, singer.getVersion()),
				() -> assertEquals(2, singer.getSongs().size()));
	}

	@Test
	void song1() {
		Song song = songs.findById(1).get();
		Song expected = new Song(1, "Limit", AggregateReference.to(1));
		assertEquals(expected, song);
	}

	@Test
	void song2() {
		Song song = songs.findById(4).get();
		Song expected = new Song(4, "暴かれた世界", AggregateReference.to(2));
		assertEquals(expected, song);
	}

	@Test
	void update1() {
		Singer singer = singers.findById(3).get();
		singer = singer.withName("Mr.Children");
		singer = singers.save(singer);
		assertEquals(3, singer.getId());
		assertEquals("Mr.Children", singer.getName());
		assertEquals(2, singer.getVersion());
	}

	@Test
	void update2() {
		Singer singer = singers.findById(3).get()
				.withVersion(0);
		assertThrows(OptimisticLockingFailureException.class, () -> singers.save(singer));
	}
}
