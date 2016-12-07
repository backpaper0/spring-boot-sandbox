package app.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.entity.Todo;
import app.service.TodoService;

@RestController
@RequestMapping("todo")
public class TodoController {

    private final TodoService service;

    public TodoController(TodoService service) {
        this.service = Objects.requireNonNull(service);
    }

    @GetMapping
    public List<Todo> findAll() {
        return service.findAll();
    }

    @GetMapping("{id}")
    public Todo find(@PathVariable Long id) {
        return service.findOne(id);
    }

    @PostMapping
    public Todo create(@RequestParam String content,
            @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate deadline) {
        return service.create(content, deadline);
    }

    @PostMapping("{id}")
    public Todo done(@PathVariable Long id) {
        return service.done(id);
    }
}
