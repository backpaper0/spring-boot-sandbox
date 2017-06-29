package com.example.demo.entity;

import java.time.LocalDateTime;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.OriginalStates;

@Entity
public class Foo {

    @Id
    public Integer id;
    public String value;
    public LocalDateTime updatedAt;

    @OriginalStates
    Foo originalStates;
}
