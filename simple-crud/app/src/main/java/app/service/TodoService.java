package app.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.entity.Todo;
import app.repository.TodoRepository;

@Service
public class TodoService {

    private final TodoRepository repository;

    public TodoService(TodoRepository repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    public List<Todo> findAll() {
        return repository.findAll();
    }

    public Todo findOne(Long id) {
        return repository.findOne(id);
    }

    @Transactional
    public Todo create(String content, LocalDate deadline) {
        Todo entity = new Todo();
        entity.content = content;
        entity.deadline = deadline;
        return repository.save(entity);
    }

    public Todo done(Long id) {
        Todo entity = repository.findOne(id);
        entity.done = true;
        repository.save(entity);
        return entity;
    }
}
