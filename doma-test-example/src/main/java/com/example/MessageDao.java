package com.example;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Result;

@Dao
@ConfigAutowireable
public interface MessageDao {

    @Select
    List<Message> selectAll();

    @Insert
    Result<Message> insert(Message entity);

    @Update
    Result<Message> update(Message entity);

    @Delete
    Result<Message> delete(Message entity);
}
