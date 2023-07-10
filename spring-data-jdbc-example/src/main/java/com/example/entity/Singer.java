package com.example.entity;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.MappedCollection;

public record Singer(
		@Id Integer id,
		String name,
		@Version Integer version,
		@MappedCollection Set<Song> songs) {

	public Singer withName(String newName) {
		return new Singer(id, newName, version, songs);
	}

	public Singer withVersion(Integer newVersion) {
		return new Singer(id, name, newVersion, songs);
	}
}
