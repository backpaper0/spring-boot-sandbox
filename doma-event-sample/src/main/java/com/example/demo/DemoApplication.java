package com.example.demo;

import java.time.LocalDateTime;
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
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import com.example.demo.dao.BarDao;
import com.example.demo.dao.FooDao;
import com.example.demo.entity.Bar;
import com.example.demo.entity.Foo;
import com.example.demo.event.PostDelete;
import com.example.demo.event.PostInsert;
import com.example.demo.event.PostUpdate;
import com.example.demo.event.PreDelete;
import com.example.demo.event.PreInsert;
import com.example.demo.event.PreUpdate;

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

    @EventListener
    public void preInsertFoo(final PreInsert<Foo> event) {
        System.out.println("*** preInsert Foo ***");
        event.getEntity().updatedAt = LocalDateTime.now();
    }

    @EventListener
    public void preInsertBar(final PreInsert<Bar> event) {
        System.out.println("+++ preInsert Bar +++");
        final Bar a = event.getEntity();
        event.getContext().setNewEntity(new Bar(a.id, a.value, LocalDateTime.now()));
    }

    @EventListener
    public void postInsertFoo(final PostInsert<Foo> event) {
        System.out.println("=== postInsert Foo ===");
    }

    @EventListener
    public void preUpdateFoo(final PreUpdate<Foo> event) {
        System.out.println("@@@ preUpdate Foo @@@");
    }

    @EventListener
    public void postUpdateFoo(final PostUpdate<Foo> event) {
        System.out.println("%%% postUpdate Foo %%%");
    }

    @EventListener
    public void preDeleteFoo(final PreDelete<Foo> event) {
        System.out.println("### preDelete Foo ###");
    }

    @EventListener
    public void postDeleteFoo(final PostDelete<Foo> event) {
        System.out.println("$$$ postDelete Foo $$$");
    }

    @EventListener
    public void preInsertFoo2(final Foo entity, final PreInsertContext<Foo> context) {
        System.out.println("+++ preInsert Foo 2 +++");
    }

    @EventListener
    public void postInsertFoo2(final Foo entity, final PostInsertContext<Foo> context) {
        System.out.println("=== postInsert Foo 2 ===");
    }

    @EventListener
    public void preUpdateFoo2(final Foo entity, final PreUpdateContext<Foo> context) {
        System.out.println("@@@ preUpdate Foo 2 @@@");
    }

    @EventListener
    public void postUpdateFoo2(final Foo entity, final PostUpdateContext<Foo> context) {
        System.out.println("%%% postUpdate Foo 2 %%%");
    }

    @EventListener
    public void preDeleteFoo2(final Foo entity, final PreDeleteContext<Foo> context) {
        System.out.println("### preDelete Foo 2 ###");
    }

    @EventListener
    public void postDeleteFoo2(final Foo entity, final PostDeleteContext<Foo> context) {
        System.out.println("$$$ postDelete Foo 2 $$$");
    }

}
