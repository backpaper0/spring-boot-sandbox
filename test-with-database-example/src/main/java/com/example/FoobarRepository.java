package com.example;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface FoobarRepository extends PagingAndSortingRepository<Foobar, Integer> {
}
