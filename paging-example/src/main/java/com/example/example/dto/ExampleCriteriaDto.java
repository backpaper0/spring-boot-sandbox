package com.example.example.dto;

import org.springframework.data.domain.Pageable;

import lombok.Data;

@Data
public class ExampleCriteriaDto {

	private String content;
	private Pageable pageable;
}
