package com.example.mapper;

import com.example.model.Message;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MessageMapper {

    int insert(Message message);

    List<Message> selectAll();
}
