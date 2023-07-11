package com.example.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.entity.Tweet;

public interface TweetRepository extends CrudRepository<Tweet, String> {
}
