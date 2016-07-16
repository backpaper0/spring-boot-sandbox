package study.dao;

import java.util.Optional;
import java.util.stream.Collector;

import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.SelectType;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import study.entity.Todo;

@Dao
@ConfigAutowireable
public interface TodoDao {

    @Select(strategy = SelectType.COLLECT)
    <R> R selectAll(Collector<Todo, ?, R> collector);

    @Select
    Optional<Todo> selectById(Long id);

    @Insert
    int insert(Todo entity);

    @Update
    int update(Todo entity);
}
