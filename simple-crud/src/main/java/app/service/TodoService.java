package app.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.entity.Todo;
import app.repository.TodoRepository;

@Service
@Transactional(readOnly = true)
public class TodoService {

    private final TodoRepository repository;

    public TodoService(final TodoRepository repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    public List<Todo> findAll() {
        return repository.findAll();
    }

    public Optional<Todo> findOne(final Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Todo create(final String content, final LocalDate deadline) {
        final Todo entity = new Todo();
        entity.setContent(content);
        entity.setDeadline(deadline);
        return repository.save(entity);
    }

    @Transactional
    public Optional<Todo> done(final Long id) {
        return repository.findById(id).map(entity -> {
            entity.setDone(true);
            return repository.save(entity);
        });
    }
}
