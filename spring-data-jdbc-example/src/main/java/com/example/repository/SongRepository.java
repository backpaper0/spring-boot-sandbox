package com.example.repository;

import com.example.entity.Song;
import org.springframework.data.repository.ListCrudRepository;

public interface SongRepository extends ListCrudRepository<Song, Integer> {}
