package com.example.controller;

import com.example.entity.Foo;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {

    @GetMapping("/foos/1")
    Object foo1() {
        return new Foo(1, "fxx");
    }

    @GetMapping("/foos")
    Object foos() {
        return List.of(new Foo(1, "fxx"), new Foo(2, "bar"));
    }

    @PostMapping("/foos/3")
    Foo postJson(@RequestBody Foo req) {
        return req;
    }
}
