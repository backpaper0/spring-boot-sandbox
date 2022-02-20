package com.example.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.entity.Singer;

public interface SingerRepository extends PagingAndSortingRepository<Singer, Integer> {
}
