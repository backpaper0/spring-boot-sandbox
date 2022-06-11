package com.example.interceptor;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InterceptorExampleMapper {

	int insert(Table6 model);

	int update(Table6 model);

	List<Table6> selectAll();
}
