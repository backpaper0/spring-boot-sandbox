package com.example.dao;

import java.util.function.Function;
import java.util.stream.Stream;
import org.seasar.doma.Dao;
import org.seasar.doma.Entity;
import org.seasar.doma.Select;
import org.seasar.doma.SelectType;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.entity.NamingType;

@Dao
@ConfigAutowireable
public interface HeaderDetailDao {

    @Select(strategy = SelectType.STREAM)
    <R> R selectAll(Function<Stream<HeaderDetail>, R> f);

    @Entity(naming = NamingType.SNAKE_UPPER_CASE)
    public static class HeaderDetail {
        public int headerId;
        public String headerName;
        public int detailId;
        public String detailName;
        public int firstId;
        public int lastId;
    }
}
