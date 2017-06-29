package com.example.demo.event;

import java.util.Objects;
import org.seasar.doma.jdbc.entity.PostDeleteContext;

public final class PostDelete<ENTITY> {

    private final ENTITY entity;
    private final PostDeleteContext<ENTITY> context;

    public PostDelete(final ENTITY entity, final PostDeleteContext<ENTITY> context) {
        this.entity = Objects.requireNonNull(entity);
        this.context = Objects.requireNonNull(context);
    }

    public ENTITY getEntity() {
        return entity;
    }

    public PostDeleteContext<ENTITY> getContext() {
        return context;
    }
}
