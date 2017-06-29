package com.example.demo.event;

import java.util.Objects;
import org.seasar.doma.jdbc.entity.PostUpdateContext;

public final class PostUpdate<ENTITY> {

    private final ENTITY entity;
    private final PostUpdateContext<ENTITY> context;

    public PostUpdate(final ENTITY entity, final PostUpdateContext<ENTITY> context) {
        this.entity = Objects.requireNonNull(entity);
        this.context = Objects.requireNonNull(context);
    }

    public ENTITY getEntity() {
        return entity;
    }

    public PostUpdateContext<ENTITY> getContext() {
        return context;
    }
}
