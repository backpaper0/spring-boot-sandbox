package com.example.entity;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;

import lombok.Value;

@Value
public class Singer {

	@Id
	Integer id;
	String name;
	@MappedCollection
	Set<Song> songs;
}
