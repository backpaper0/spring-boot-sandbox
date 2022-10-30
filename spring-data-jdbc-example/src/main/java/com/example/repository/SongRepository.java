package com.example.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.entity.Song;

public interface SongRepository extends CrudRepository<Song, Integer> {
}
