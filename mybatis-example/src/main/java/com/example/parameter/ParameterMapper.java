package com.example.parameter;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ParameterMapper {

	String select1Param(int id);

	List<String> select2ParamsWithName(int p1, int p2);

	List<String> select2ParamsWithIndex(int a, int b);
}
