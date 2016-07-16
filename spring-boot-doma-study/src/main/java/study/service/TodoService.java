package study.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import study.dao.TodoDao;
import study.entity.Todo;

@Service
public class TodoService {

    private final TodoDao dao;

    @Autowired
    public TodoService(TodoDao dao) {
        this.dao = dao;
    }

    public List<Todo> findAll() {
        return dao.selectAll(Collectors.toList());
    }

    public Optional<Todo> findById(Long id) {
        return dao.selectById(id);
    }

    @Transactional
    public Todo create(String content) {
        Todo entity = new Todo();
        entity.content = content;
        entity.createdAt = LocalDateTime.now();
        dao.insert(entity);
        return entity;
    }

    @Transactional
    public Optional<Todo> close(Long id) {
        return dao.selectById(id).map(entity -> {
            entity.doneAt = LocalDateTime.now();
            dao.update(entity);
            return entity;
        });
    }
}
