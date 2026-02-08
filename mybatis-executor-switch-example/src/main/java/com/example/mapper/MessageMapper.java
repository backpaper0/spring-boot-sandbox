package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.model.Message;

@Mapper
public interface MessageMapper {

    int insert(Message message);

    List<Message> selectAll();
}
