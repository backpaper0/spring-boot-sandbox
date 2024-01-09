package com.example.repository;

import org.springframework.data.repository.ListCrudRepository;

import com.example.entity.Singer;

public interface SingerRepository extends ListCrudRepository<Singer, Integer> {
}
