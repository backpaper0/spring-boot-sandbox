package com.example.demo.even2;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.seasar.doma.jdbc.entity.PostDeleteContext;
import org.seasar.doma.jdbc.entity.PostInsertContext;
import org.seasar.doma.jdbc.entity.PostUpdateContext;
import org.seasar.doma.jdbc.entity.PreDeleteContext;
import org.seasar.doma.jdbc.entity.PreInsertContext;
import org.seasar.doma.jdbc.entity.PreUpdateContext;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListenerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(0)
public class DomaEventListenerFactory implements EventListenerFactory, ApplicationContextAware {

    private final List<Class<?>> contextClasses = Arrays.asList(
            PreInsertContext.class,
            PostInsertContext.class,
            PreUpdateContext.class,
            PostUpdateContext.class,
            PreDeleteContext.class,
            PostDeleteContext.class);

    private ApplicationContext applicationContext;

    @Override
    public boolean supportsMethod(final Method method) {
        return method.getParameterCount() == 2
                && contextClasses.contains(method.getParameterTypes()[1]);
    }

    @Override
    public ApplicationListener<?> createApplicationListener(final String beanName,
            final Class<?> type,
            final Method method) {
        return new DomaApplicationListener(applicationContext, beanName, type, method);
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = Objects.requireNonNull(applicationContext);
    }
}
