package com.example.demo.event;

import java.lang.reflect.Method;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListenerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class DomaEventListenerFactory implements EventListenerFactory, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public boolean supportsMethod(final Method method) {
        return AnnotationUtils.findAnnotation(method, DomaEventListener.class) != null;
    }

    @Override
    public ApplicationListener<?> createApplicationListener(final String beanName,
            final Class<?> type,
            final Method method) {
        final DomaEventListener domaEventListener = AnnotationUtils.findAnnotation(method,
                DomaEventListener.class);
        final Class<?> targetContextClass = domaEventListener.value();
        return new DomaApplicationListener(targetContextClass, applicationContext, beanName, type,
                method);
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }
}
