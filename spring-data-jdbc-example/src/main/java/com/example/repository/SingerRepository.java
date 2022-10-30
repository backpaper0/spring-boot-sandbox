package com.example.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.entity.Singer;

public interface SingerRepository extends CrudRepository<Singer, Integer> {
}
