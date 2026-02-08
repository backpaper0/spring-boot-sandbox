package com.example.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.mapper.MessageMapper;
import com.example.model.Message;

@Service
@Transactional
public class MessageService {

    @Autowired
    private MessageMapper mapper;
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    public Message insert(String content) {
        Message message = new Message();
        message.setContent(content);
        mapper.insert(message);
        return message;
    }

    public List<Message> bulkInsert(List<String> contents) {
        List<Message> messages = new ArrayList<>();
        try (SqlSession session = sqlSessionFactory.openSession(ExecutorType.SIMPLE)) {
            MessageMapper batchMapper = session.getMapper(MessageMapper.class);
            for (String content : contents) {
                Message message = new Message();
                message.setContent(content);
                batchMapper.insert(message);
                messages.add(message);
            }
            session.flushStatements();
        }
        return messages;
    }

    @Transactional(readOnly = true)
    public List<Message> selectAll() {
        return mapper.selectAll();
    }
}
