package com.example.repository;

import com.example.entity.Singer;
import org.springframework.data.repository.ListCrudRepository;

public interface SingerRepository extends ListCrudRepository<Singer, Integer> {}
