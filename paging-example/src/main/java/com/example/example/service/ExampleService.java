package com.example.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.example.dto.ExampleCriteriaDto;
import com.example.example.dto.ExampleDto;
import com.example.example.mapper.ExampleMapper;

@Service
@Transactional
public class ExampleService {

	@Autowired
	private ExampleMapper mapper;

	@Transactional(readOnly = true)
	public Page<ExampleDto> selectExamples(ExampleCriteriaDto dto) {
		int total = mapper.countExampleByCriteria(dto);
		List<ExampleDto> content = mapper.selectExampleByCriteria(dto);
		return new PageImpl<>(content, dto.getPageable(), total);
	}
}
