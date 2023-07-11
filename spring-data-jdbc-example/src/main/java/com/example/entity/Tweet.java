package com.example.entity;

import org.springframework.data.annotation.Id;

public record Tweet(
		@Id String id,
		String content) {
}
