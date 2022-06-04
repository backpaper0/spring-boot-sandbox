package com.example.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Singer;
import com.example.model.Song;

@RestController
@RequestMapping("/songs")
public class SongController {

	@GetMapping
	public Object getList(@Validated SongCriteria criteria) {
		var author1 = new Singer(1, "LUNA SEA");
		var author2 = new Singer(2, "Mr.Children");
		var songs = List.of(
				new Song(1, "Limit", author1),
				new Song(2, "MECHANICAL DANCE", author1),
				new Song(3, "Unlikelihood", author1),
				new Song(4, "デルモ", author2),
				new Song(5, "UFO", author2));
		if (criteria.singer() != null) {
			songs = songs.stream().filter(a -> a.singer().id() == criteria.singer().intValue()).toList();
		}
		return Map.of("songs", songs);
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Object create(@RequestBody SongCreation request) {
		var author1 = new Singer(request.singer(), "LUNA SEA");
		return new Song(10, request.title(), author1);
	}
}
