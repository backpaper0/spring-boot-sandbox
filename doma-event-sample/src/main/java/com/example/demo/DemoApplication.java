package com.example.demo;

import java.util.Objects;

import org.seasar.doma.jdbc.entity.PostDeleteContext;
import org.seasar.doma.jdbc.entity.PostInsertContext;
import org.seasar.doma.jdbc.entity.PostUpdateContext;
import org.seasar.doma.jdbc.entity.PreDeleteContext;
import org.seasar.doma.jdbc.entity.PreInsertContext;
import org.seasar.doma.jdbc.entity.PreUpdateContext;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.dao.FooDao;
import com.example.demo.entity.Foo;
import com.example.demo.event.HandlePostDelete;
import com.example.demo.event.HandlePostInsert;
import com.example.demo.event.HandlePostUpdate;
import com.example.demo.event.HandlePreDelete;
import com.example.demo.event.HandlePreInsert;
import com.example.demo.event.HandlePreUpdate;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    public static void main(final String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    private final FooDao fooDao;

    public DemoApplication(final FooDao fooDao) {
        this.fooDao = Objects.requireNonNull(fooDao);
    }

    @Override
    public void run(final String... args) throws Exception {

        final Foo foo2 = new Foo();
        foo2.id = 2;
        foo2.value = "bbb";
        fooDao.insert(foo2);

        fooDao.selectById(1).ifPresent(foo1 -> {
            foo1.value = "AAA";
            fooDao.update(foo1);
        });

        fooDao.selectById(3).ifPresent(fooDao::delete);
    }

    @HandlePreInsert
    public void preInsertFoo(final Foo entity) {
        log("preInsert Foo");
    }

    @HandlePostInsert
    public void postInsertFoo(final Foo entity) {
        log("postInsert Foo");
    }

    @HandlePreUpdate
    public void preUpdateFoo(final Foo entity) {
        log("preUpdate Foo");
    }

    @HandlePostUpdate
    public void postUpdateFoo(final Foo entity) {
        log("postUpdate Foo");
    }

    @HandlePreDelete
    public void preDeleteFoo(final Foo entity) {
        log("preDelete Foo");
    }

    @HandlePostDelete
    public void postDeleteFoo(final Foo entity) {
        log("postDelete Foo");
    }

    @HandlePreInsert
    public void preInsertFoo(final Foo entity, final PreInsertContext<Foo> context) {
        log("preInsert Foo, Context");
    }

    @HandlePostInsert
    public void postInsertFoo(final Foo entity, final PostInsertContext<Foo> context) {
        log("postInsert Foo, Context");
    }

    @HandlePreUpdate
    public void preUpdateFoo(final Foo entity, final PreUpdateContext<Foo> context) {
        log("preUpdate Foo, Context");
    }

    @HandlePostUpdate
    public void postUpdateFoo(final Foo entity, final PostUpdateContext<Foo> context) {
        log("postUpdate Foo, Context");
    }

    @HandlePreDelete
    public void preDeleteFoo(final Foo entity, final PreDeleteContext<Foo> context) {
        log("preDelete Foo, Context");
    }

    @HandlePostDelete
    public void postDeleteFoo(final Foo entity, final PostDeleteContext<Foo> context) {
        log("postDelete Foo, Context");
    }

    static void log(final String s) {
        System.out.printf("******** %s ********%n", s);
    }
}
