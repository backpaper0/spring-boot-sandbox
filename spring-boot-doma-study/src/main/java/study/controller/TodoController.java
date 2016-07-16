package study.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import study.entity.Todo;
import study.service.TodoService;

@RestController
@RequestMapping("/todo")
public class TodoController {

    private final TodoService service;

    @Autowired
    public TodoController(TodoService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.GET)
    List<Todo> findAll() {
        return service.findAll();
    }

    @RequestMapping(method = RequestMethod.GET, value = "{id}")
    Optional<Todo> findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    Todo create(@RequestParam String content) {
        return service.create(content);
    }

    @RequestMapping(method = RequestMethod.POST, value = "{id}:close")
    Optional<Todo> close(@PathVariable Long id) {
        return service.close(id);
    }
}
