package com.example.demo.event;

import java.util.Objects;
import org.seasar.doma.jdbc.entity.PreInsertContext;

public final class PreInsert<ENTITY> {

    private final ENTITY entity;
    private final PreInsertContext<ENTITY> context;

    public PreInsert(final ENTITY entity, final PreInsertContext<ENTITY> context) {
        this.entity = Objects.requireNonNull(entity);
        this.context = Objects.requireNonNull(context);
    }

    public ENTITY getEntity() {
        return entity;
    }

    public PreInsertContext<ENTITY> getContext() {
        return context;
    }
}
