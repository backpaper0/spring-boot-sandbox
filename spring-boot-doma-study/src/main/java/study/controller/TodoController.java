package study.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import study.controller.exception.NotFoundException;
import study.domain.Content;
import study.domain.Key;
import study.entity.Todo;
import study.service.TodoService;

@RestController
@RequestMapping("/todo")
public class TodoController {

    private final TodoService service;

    public TodoController(TodoService service) {
        this.service = service;
    }

    /**
     * 全件取ってくる。
     * 
     * @return
     */
    @GetMapping
    List<Todo> findAll() {
        return service.findAll();
    }

    /**
     * idで1件だけ取ってくる。
     * 無ければ404 Not Found。
     * 
     * @param id
     * @return
     * @throws NotFoundException
     */
    @GetMapping("{id}")
    Todo findById(@PathVariable Key<Todo> id) {
        return service.findById(id).orElseThrow(NotFoundException::new);
    }

    /**
     * Todoを作る。
     * 
     * @param content
     * @return
     */
    @PostMapping
    Todo create(@RequestParam Content content) {
        return service.create(content);
    }

    /**
     * 渡されたidに該当するTodoを終了する。
     * 無ければ404 Not Found。
     * 
     * @param id
     * @return
     * @throws NotFoundException
     */
    @PostMapping("{id}:close")
    Todo close(@PathVariable Key<Todo> id) {
        return service.close(id).orElseThrow(NotFoundException::new);
    }

    //    /**
    //     * NoSuchElementExceptionが投げられたら404 Not Foundにする。
    //     * 
    //     * @param e
    //     */
    //    @ExceptionHandler(NoSuchElementException.class)
    //    @ResponseStatus(HttpStatus.NOT_FOUND)
    //    void notFound(NoSuchElementException e) {
    //    }
}
