package com.example.demo.dao;

import java.util.Optional;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import com.example.demo.entity.Foo;

@Dao
@ConfigAutowireable
public interface FooDao {

    @Select
    Optional<Foo> selectById(Integer id);

    @Insert
    int insert(Foo entity);

    @Update
    int update(Foo entity);

    @Delete
    int delete(Foo entity);
}
