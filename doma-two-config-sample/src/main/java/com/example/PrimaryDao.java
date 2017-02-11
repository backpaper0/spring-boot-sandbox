package com.example;

import org.seasar.doma.Dao;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Config;

@Dao
@ConfigAutowireable
public interface PrimaryDao {

    default Config getInjectedConfig() {
        return Config.get(this);
    }
}
