package com.example;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MessageService {

	private final MessageRepository repository;

	public MessageService(MessageRepository repository) {
		this.repository = repository;
	}

	public List<Message> findAll() {
		return repository.findAll();
	}

	public Message create(String content) {
		Message message = new Message();
		message.setContent(content);
		return repository.save(message);
	}
}
