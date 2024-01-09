package com.example.repository;

import org.springframework.data.repository.ListCrudRepository;

import com.example.entity.Song;

public interface SongRepository extends ListCrudRepository<Song, Integer> {
}
