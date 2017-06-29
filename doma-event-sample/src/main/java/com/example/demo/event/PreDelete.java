package com.example.demo.event;

import java.util.Objects;
import org.seasar.doma.jdbc.entity.PreDeleteContext;

public final class PreDelete<ENTITY> {

    private final ENTITY entity;
    private final PreDeleteContext<ENTITY> context;

    public PreDelete(final ENTITY entity, final PreDeleteContext<ENTITY> context) {
        this.entity = Objects.requireNonNull(entity);
        this.context = Objects.requireNonNull(context);
    }

    public ENTITY getEntity() {
        return entity;
    }

    public PreDeleteContext<ENTITY> getContext() {
        return context;
    }
}
