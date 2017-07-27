package com.example.demo.event;

import java.lang.reflect.Method;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListenerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class DomaEventListenerFactory implements EventListenerFactory, BeanFactoryAware {

    private BeanFactory beanFactory;

    @Override
    public boolean supportsMethod(final Method method) {
        return AnnotationUtils.findAnnotation(method, DomaEventHandler.class) != null;
    }

    @Override
    public ApplicationListener<?> createApplicationListener(final String beanName,
            final Class<?> type,
            final Method method) {
        final DomaEventHandler domaEventListener = AnnotationUtils.findAnnotation(method,
                DomaEventHandler.class);
        final Class<?> targetContextClass = domaEventListener.value();
        return new DomaApplicationListener(targetContextClass, beanFactory, beanName, type,
                method);
    }

    @Override
    public void setBeanFactory(final BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
