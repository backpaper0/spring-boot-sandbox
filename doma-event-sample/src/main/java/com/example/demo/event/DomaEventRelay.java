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
        publisher.publishEvent(new DomaEvent(entity, context));
    }

    @Override
    public void preUpdate(final Object entity, final PreUpdateContext<Object> context) {
        publisher.publishEvent(new DomaEvent(entity, context));
    }

    @Override
    public void preDelete(final Object entity, final PreDeleteContext<Object> context) {
        publisher.publishEvent(new DomaEvent(entity, context));
    }

    @Override
    public void postInsert(final Object entity, final PostInsertContext<Object> context) {
        publisher.publishEvent(new DomaEvent(entity, context));
    }

    @Override
    public void postUpdate(final Object entity, final PostUpdateContext<Object> context) {
        publisher.publishEvent(new DomaEvent(entity, context));
    }

    @Override
    public void postDelete(final Object entity, final PostDeleteContext<Object> context) {
        publisher.publishEvent(new DomaEvent(entity, context));
    }

    @Override
    public void setApplicationEventPublisher(
            final ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = Objects.requireNonNull(applicationEventPublisher);
    }
}
