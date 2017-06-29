package com.example.demo.event;

import java.util.Objects;
import org.seasar.doma.jdbc.entity.PostInsertContext;

public final class PostInsert<ENTITY> {

    private final ENTITY entity;
    private final PostInsertContext<ENTITY> context;

    public PostInsert(final ENTITY entity, final PostInsertContext<ENTITY> context) {
        this.entity = Objects.requireNonNull(entity);
        this.context = Objects.requireNonNull(context);
    }

    public ENTITY getEntity() {
        return entity;
    }

    public PostInsertContext<ENTITY> getContext() {
        return context;
    }
}
