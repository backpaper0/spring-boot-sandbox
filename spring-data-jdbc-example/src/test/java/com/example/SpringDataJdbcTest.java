package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Set;

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
		Singer expected = new Singer(1, "LUNA SEA", 1, Set.of(
				new Song(1, "Limit", AggregateReference.to(1)),
				new Song(2, "Unlikelihood", AggregateReference.to(1)),
				new Song(3, "MECHANICAL DANCE", AggregateReference.to(1))));
		assertEquals(expected, singer);
	}

	@Test
	void singer2() {
		Singer singer = singers.findById(2).get();
		Singer expected = new Singer(2, "THEE MICHELLE GUN ELEPHANT", 1, Set.of(
				new Song(4, "暴かれた世界", AggregateReference.to(2)),
				new Song(5, "G.W.D", AggregateReference.to(2))));
		assertEquals(expected, singer);
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
		assertEquals(3, singer.id());
		assertEquals("Mr.Children", singer.name());
		assertEquals(2, singer.version());
	}

	@Test
	void update2() {
		Singer singer = singers.findById(3).get()
				.withVersion(0);
		assertThrows(OptimisticLockingFailureException.class, () -> singers.save(singer));
	}
}
