package com.example.repository;

import com.example.entity.Tweet;
import org.springframework.data.repository.CrudRepository;

public interface TweetRepository extends CrudRepository<Tweet, String> {}
