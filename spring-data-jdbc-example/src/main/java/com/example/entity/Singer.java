package com.example.entity;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.MappedCollection;

import lombok.Value;
import lombok.With;

@Value
@With
public class Singer {

	@Id
	Integer id;
	String name;
	@Version
	Integer version;
	@MappedCollection
	Set<Song> songs;
}
