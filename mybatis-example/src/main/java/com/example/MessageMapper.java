package com.example;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.cursor.Cursor;

@Mapper
public interface MessageMapper {

	@Select("SELECT id, content FROM messages ORDER BY id ASC")
	List<Message> selectAll1();

	List<Message> selectAll2();
}
