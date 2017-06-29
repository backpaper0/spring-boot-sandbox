package com.example.demo.event;

import java.util.Objects;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.core.ResolvableType;

public final class EntityEvent<T> extends PayloadApplicationEvent<T> {

    private final Class<?> entityClass;

    public EntityEvent(final T payload, final Class<?> entityClass) {
        super(payload, payload);
        this.entityClass = Objects.requireNonNull(entityClass);
    }

    @Override
    public ResolvableType getResolvableType() {
        return ResolvableType.forClassWithGenerics(getClass(),
                ResolvableType.forClassWithGenerics(getPayload().getClass(), entityClass));
    }
}
