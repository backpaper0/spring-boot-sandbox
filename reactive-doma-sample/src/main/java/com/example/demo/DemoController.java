package com.example.demo;

import java.util.Objects;

import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@RestController
public class DemoController {

    private final DemoDao dao;

    public DemoController(final DemoDao dao) {
        this.dao = Objects.requireNonNull(dao);
    }

    @GetMapping(path = "/demo", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<DemoEntity> get() {

        final Publisher<DemoEntity> p = dao.selectAllPublisher();

        return Flux.from(p)
                .subscribeOn(Schedulers.elastic())
                .map(b -> b.value(b.value + ":" + Thread.currentThread().getName()));
    }
}
