package com.example.demo;

import java.util.Objects;

import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

@Entity(immutable = true)
@Table(name = "demo_values")
public class DemoEntity {

    private static final Long unassignedId = new Long(-1);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public final Long id;
    public final String value;

    DemoEntity(final Long id, final String value) {
        this.id = (unassignedId == id) ? null : Objects.requireNonNull(id);
        this.value = Objects.requireNonNull(value);
    }

    public DemoEntity(final String value) {
        this(unassignedId, value);
    }

    public DemoEntity value(final String value) {
        return new DemoEntity(id, value);
    }
}
