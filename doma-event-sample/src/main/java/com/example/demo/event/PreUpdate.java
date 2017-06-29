package com.example.demo.event;

import java.util.Objects;
import org.seasar.doma.jdbc.entity.PreUpdateContext;

public final class PreUpdate<ENTITY> {

    private final ENTITY entity;
    private final PreUpdateContext<ENTITY> context;

    public PreUpdate(final ENTITY entity, final PreUpdateContext<ENTITY> context) {
        this.entity = Objects.requireNonNull(entity);
        this.context = Objects.requireNonNull(context);
    }

    public ENTITY getEntity() {
        return entity;
    }

    public PreUpdateContext<ENTITY> getContext() {
        return context;
    }
}
