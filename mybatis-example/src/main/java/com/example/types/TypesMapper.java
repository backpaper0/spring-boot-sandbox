package com.example.types;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TypesMapper {

	Model4 selectById(int id);
}
