package com.example.example.form;

import java.io.Serializable;

import org.springframework.beans.BeanUtils;

import com.example.example.dto.ExampleCriteriaDto;

import lombok.Data;

@Data
public class ExampleForm implements Serializable {

	private String content;

	public ExampleCriteriaDto toExampleCriteriaDto() {
		ExampleCriteriaDto dto = new ExampleCriteriaDto();
		BeanUtils.copyProperties(this, dto);
		return dto;
	}
}
