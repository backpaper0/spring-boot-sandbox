package com.example.resultmaps;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ResultMapsMapper {

	List<Model1> selectAll();
}
