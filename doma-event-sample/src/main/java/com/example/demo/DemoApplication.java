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
import org.springframework.stereotype.Component;
import com.example.demo.dao.BarDao;
import com.example.demo.dao.FooDao;
import com.example.demo.entity.Bar;
import com.example.demo.entity.Foo;
import com.example.demo.event.PostDeleteEventListener;
import com.example.demo.event.PostInsertEventListener;
import com.example.demo.event.PostUpdateEventListener;
import com.example.demo.event.PreDeleteEventListener;
import com.example.demo.event.PreInsertEventListener;
import com.example.demo.event.PreUpdateEventListener;

@SpringBootApplication
public class DemoApplication {

    public static void main(final String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}

@Component
class Runner implements CommandLineRunner {

    private final FooDao fooDao;
    private final BarDao barDao;

    public Runner(final FooDao fooDao, final BarDao barDao) {
        this.fooDao = Objects.requireNonNull(fooDao);
        this.barDao = Objects.requireNonNull(barDao);
    }

    @Override
    public void run(final String... args) throws Exception {

        final Foo foo2 = new Foo();
        foo2.id = 2;
        foo2.value = "bbb";
        fooDao.insert(foo2);

        final Bar bar2 = new Bar(2, "yyy", null);
        barDao.insert(bar2);

        fooDao.selectById(1).ifPresent(foo1 -> {
            foo1.value = "AAA";
            fooDao.update(foo1);
        });

        barDao.selectById(1).ifPresent(bar1 -> {
            barDao.update(new Bar(bar1.id, "BBB", bar1.updatedAt));
        });

        fooDao.selectById(3).ifPresent(fooDao::delete);

        barDao.selectById(3).ifPresent(barDao::delete);
    }

    @PreInsertEventListener
    public void preInsertFoo(final Foo entity) {
        log("preInsert Foo");
    }

    @PostInsertEventListener
    public void postInsertFoo(final Foo entity) {
        log("postInsert Foo");
    }

    @PreUpdateEventListener
    public void preUpdateFoo(final Foo entity) {
        log("preUpdate Foo");
    }

    @PostUpdateEventListener
    public void postUpdateFoo(final Foo entity) {
        log("postUpdate Foo");
    }

    @PreDeleteEventListener
    public void preDeleteFoo(final Foo entity) {
        log("preDelete Foo");
    }

    @PostDeleteEventListener
    public void postDeleteFoo(final Foo entity) {
        log("postDelete Foo");
    }

    @PreInsertEventListener
    public void preInsertFoo(final Foo entity, final PreInsertContext<Foo> context) {
        log("preInsert Foo, Context");
    }

    @PostInsertEventListener
    public void postInsertFoo(final Foo entity, final PostInsertContext<Foo> context) {
        log("postInsert Foo, Context");
    }

    @PreUpdateEventListener
    public void preUpdateFoo(final Foo entity, final PreUpdateContext<Foo> context) {
        log("preUpdate Foo, Context");
    }

    @PostUpdateEventListener
    public void postUpdateFoo(final Foo entity, final PostUpdateContext<Foo> context) {
        log("postUpdate Foo, Context");
    }

    @PreDeleteEventListener
    public void preDeleteFoo(final Foo entity, final PreDeleteContext<Foo> context) {
        log("preDelete Foo, Context");
    }

    @PostDeleteEventListener
    public void postDeleteFoo(final Foo entity, final PostDeleteContext<Foo> context) {
        log("postDelete Foo, Context");
    }

    static void log(final String s) {
        System.out.printf("******** %s ********%n", s);
    }
}
