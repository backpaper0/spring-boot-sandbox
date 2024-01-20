package com.example.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Foo;

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
