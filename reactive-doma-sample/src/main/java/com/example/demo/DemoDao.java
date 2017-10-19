package com.example.demo;

import java.util.UUID;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.reactivestreams.Publisher;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Suppress;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Result;
import org.seasar.doma.message.Message;

@Dao
@ConfigAutowireable
public interface DemoDao {

    @Select
    @Suppress(messages = Message.DOMA4274)
    Stream<DemoEntity> selectAll();

    default Publisher<DemoEntity> selectAllPublisher() {
        return new DemoPublisher<>(this::selectAll);
    }

    @Delete(sqlFile = true)
    int deleteAll();

    @Insert
    Result<DemoEntity> insert(DemoEntity entity);

    default void setUp() {
        deleteAll();
        IntStream.range(0, 100)
                .forEach(i -> insert(new DemoEntity(UUID.randomUUID().toString())));
    }
}
