package com.example.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

public record Song(
		@Id Integer id,
		String title,
		AggregateReference<Singer, Integer> singer) {
}
