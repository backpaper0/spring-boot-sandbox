package com.example;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.cursor.Cursor;

import com.example.aop.MyAop;

@Mapper
@MyAop
public interface MessageMapper {

	@Select("SELECT id, content FROM messages ORDER BY id ASC")
	Cursor<Message> selectAll();
}
