package app.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import app.entity.Todo;
import app.service.TodoService;

@RestController
@RequestMapping("todo")
public class TodoController {

    private final TodoService service;

    public TodoController(final TodoService service) {
        this.service = Objects.requireNonNull(service);
    }

    @GetMapping
    public List<TodoView> findAll() {
        return service.findAll().stream().map(TodoView::new).collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public TodoView find(@PathVariable final Long id) {
        return service.findOne(id).map(TodoView::new).orElseThrow(TodoNotFoundException::new);
    }

    @PostMapping
    public TodoView create(@RequestParam final String content,
            @RequestParam(required = false) @DateTimeFormat(
                    iso = ISO.DATE) final LocalDate deadline) {
        return new TodoView(service.create(content, deadline));
    }

    @PostMapping("{id}/done")
    public TodoView done(@PathVariable final Long id) {
        return service.done(id).map(TodoView::new).orElseThrow(TodoNotFoundException::new);
    }

    public static class TodoView {

        private final Long id;
        private final String content;
        private final LocalDate deadline;
        private final boolean done;

        public TodoView(final Todo entity) {
            this.id = entity.getId();
            this.content = entity.getContent();
            this.deadline = entity.getDeadline();
            this.done = entity.isDone();
        }

        public Long getId() {
            return id;
        }

        public String getContent() {
            return content;
        }

        public LocalDate getDeadline() {
            return deadline;
        }

        public boolean isDone() {
            return done;
        }
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public static class TodoNotFoundException extends RuntimeException {
    }
}
