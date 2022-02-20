package com.example.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import lombok.Value;

@Value
public class Song {

	@Id
	Integer id;
	String title;
	AggregateReference<Singer, Integer> singer;
}
