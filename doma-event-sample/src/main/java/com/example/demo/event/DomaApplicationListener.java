package com.example.demo.event;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.util.ReflectionUtils;

public class DomaApplicationListener implements ApplicationListener<DomaEvent> {

    private final Class<?> targetContextClass;
    private final BeanFactory beanFactory;
    private final String beanName;
    private final Class<?> type;
    private final Method method;

    public DomaApplicationListener(final Class<?> targetContextClass,
            final BeanFactory beanFactory,
            final String beanName,
            final Class<?> type, final Method method) {
        this.targetContextClass = Objects.requireNonNull(targetContextClass);
        this.beanFactory = Objects.requireNonNull(beanFactory);
        this.beanName = Objects.requireNonNull(beanName);
        this.type = Objects.requireNonNull(type);
        this.method = Objects.requireNonNull(method);
    }

    @Override
    public void onApplicationEvent(final DomaEvent event) {
        final Object entity = event.getSource();
        final Object context = event.getContext();
        final Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes[0].isAssignableFrom(entity.getClass())
                && targetContextClass.isAssignableFrom(context.getClass())) {
            final List<Object> args = new ArrayList<>();
            args.add(entity);
            if (parameterTypes.length > 1
                    && parameterTypes[1].isAssignableFrom(targetContextClass)) {
                args.add(context);
            }
            ReflectionUtils.makeAccessible(method);
            final Object target = beanFactory.getBean(beanName);
            ReflectionUtils.invokeMethod(method, target, args.toArray());
        }
    }
}
