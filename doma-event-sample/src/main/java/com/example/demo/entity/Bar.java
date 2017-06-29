package com.example.demo.entity;

import java.time.LocalDateTime;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;

@Entity(immutable = true)
public class Bar {

    @Id
    public final Integer id;
    public final String value;
    public final LocalDateTime updatedAt;

    public Bar(final Integer id, final String value, final LocalDateTime updatedAt) {
        this.id = id;
        this.value = value;
        this.updatedAt = updatedAt;
    }
}
