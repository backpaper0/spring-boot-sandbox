package study.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
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

    /**
     * 全件取ってくる。
     * 
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    List<Todo> findAll() {
        return service.findAll();
    }

    /**
     * idで1件だけ取ってくる。
     * 無ければ404 Not Found。
     * 
     * @param id
     * @return
     * @see #notFound(NoSuchElementException)
     */
    @RequestMapping(method = RequestMethod.GET, value = "{id}")
    Todo findById(@PathVariable Long id) {
        return service.findById(id).get();
    }

    /**
     * Todoを作る。
     * 
     * @param content
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    Todo create(@RequestParam String content) {
        return service.create(content);
    }

    /**
     * 渡されたidに該当するTodoを終了する。
     * 無ければ404 Not Found。
     * 
     * @param id
     * @return
     * @see #notFound(NoSuchElementException)
     */
    @RequestMapping(method = RequestMethod.POST, value = "{id}:close")
    Todo close(@PathVariable Long id) {
        return service.close(id).get();
    }

    /**
     * NoSuchElementExceptionが投げられたら404 Not Foundにする。
     * 
     * @param e
     */
    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    void notFound(NoSuchElementException e) {
    }
}
