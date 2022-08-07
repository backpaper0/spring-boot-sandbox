package com.example.numbering;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NumberingMapper {

	long nextval(String name);
}
