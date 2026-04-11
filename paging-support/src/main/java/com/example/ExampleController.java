package com.example;

import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {

    @Autowired
    private ExampleRepository repository;

    @GetMapping("/list")
    public Object list(Pageable pageable) {
        var sort = pageable.getSort().stream()
                .map(order -> "%1$s %2$s".formatted(order.getProperty(), order.getDirection()))
                .collect(Collectors.joining(", "));
        return Map.of(
                "offset", pageable.getOffset(),
                "pageNumber", pageable.getPageNumber(),
                "pageSize", pageable.getPageSize(),
                "sort", sort);
    }

    @GetMapping("/examples")
    public PagedModel<Example> pagedModel(Pageable pageable) {
        var page = repository.findAll(pageable);
        return new PagedModel<>(page);
    }
}
