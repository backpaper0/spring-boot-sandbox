package com.example.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.entity.Song;

public interface SongRepository extends PagingAndSortingRepository<Song, Integer> {
}
