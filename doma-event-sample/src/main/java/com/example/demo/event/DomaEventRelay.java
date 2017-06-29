package com.example.demo.event;

import java.util.Objects;
import org.seasar.doma.jdbc.entity.NullEntityListener;
import org.seasar.doma.jdbc.entity.PostDeleteContext;
import org.seasar.doma.jdbc.entity.PostInsertContext;
import org.seasar.doma.jdbc.entity.PostUpdateContext;
import org.seasar.doma.jdbc.entity.PreDeleteContext;
import org.seasar.doma.jdbc.entity.PreInsertContext;
import org.seasar.doma.jdbc.entity.PreUpdateContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

@Component
public class DomaEventRelay extends NullEntityListener<Object>
        implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher publisher;

    @Override
    public void preInsert(final Object entity, final PreInsertContext<Object> context) {
        final PreInsert<?> payload = new PreInsert<>(entity, context);
        final EntityEvent<?> event = new EntityEvent<>(payload, entity.getClass());
        publisher.publishEvent(event);
    }

    @Override
    public void preUpdate(final Object entity, final PreUpdateContext<Object> context) {
        final PreUpdate<?> payload = new PreUpdate<>(entity, context);
        final EntityEvent<?> event = new EntityEvent<>(payload, entity.getClass());
        publisher.publishEvent(event);
    }

    @Override
    public void preDelete(final Object entity, final PreDeleteContext<Object> context) {
        final PreDelete<?> payload = new PreDelete<>(entity, context);
        final EntityEvent<?> event = new EntityEvent<>(payload, entity.getClass());
        publisher.publishEvent(event);
    }

    @Override
    public void postInsert(final Object entity, final PostInsertContext<Object> context) {
        final PostInsert<?> payload = new PostInsert<>(entity, context);
        final EntityEvent<?> event = new EntityEvent<>(payload, entity.getClass());
        publisher.publishEvent(event);
    }

    @Override
    public void postUpdate(final Object entity, final PostUpdateContext<Object> context) {
        final PostUpdate<?> payload = new PostUpdate<>(entity, context);
        final EntityEvent<?> event = new EntityEvent<>(payload, entity.getClass());
        publisher.publishEvent(event);
    }

    @Override
    public void postDelete(final Object entity, final PostDeleteContext<Object> context) {
        final PostDelete<?> payload = new PostDelete<>(entity, context);
        final EntityEvent<?> event = new EntityEvent<>(payload, entity.getClass());
        publisher.publishEvent(event);
    }

    @Override
    public void setApplicationEventPublisher(
            final ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = Objects.requireNonNull(applicationEventPublisher);
    }
}
