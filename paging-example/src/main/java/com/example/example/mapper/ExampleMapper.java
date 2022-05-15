package com.example.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.example.dto.ExampleCriteriaDto;
import com.example.example.dto.ExampleDto;

@Mapper
public interface ExampleMapper {

	int countExampleByCriteria(ExampleCriteriaDto dto);

	List<ExampleDto> selectExampleByCriteria(ExampleCriteriaDto dto);
}
