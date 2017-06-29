package com.example.demo.dao;

import java.util.Optional;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Result;
import com.example.demo.entity.Bar;

@Dao
@ConfigAutowireable
public interface BarDao {

    @Select
    Optional<Bar> selectById(Integer id);

    @Insert
    Result<Bar> insert(Bar entity);

    @Update
    Result<Bar> update(Bar entity);

    @Delete
    Result<Bar> delete(Bar entity);
}
