package com.example;

import org.seasar.doma.Dao;
import org.seasar.doma.jdbc.Config;

@Dao
@SecondaryConfigAutowireable
public interface SecondaryDao {

    default Config getInjectedConfig() {
        return Config.get(this);
    }
}
