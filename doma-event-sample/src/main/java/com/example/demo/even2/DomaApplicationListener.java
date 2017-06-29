package com.example.demo.even2;

import java.lang.reflect.Method;
import java.util.Objects;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.util.ReflectionUtils;

public class DomaApplicationListener implements ApplicationListener<DomaApplicationEvent> {

    private final ApplicationContext applicationContext;
    private final String beanName;
    private final Class<?> type;
    private final Method method;

    public DomaApplicationListener(final ApplicationContext applicationContext,
            final String beanName,
            final Class<?> type, final Method method) {
        this.applicationContext = Objects.requireNonNull(applicationContext);
        this.beanName = Objects.requireNonNull(beanName);
        this.type = Objects.requireNonNull(type);
        this.method = Objects.requireNonNull(method);
    }

    @Override
    public void onApplicationEvent(final DomaApplicationEvent event) {
        final Object entity = event.getSource();
        final Object context = event.getContext();
        if (method.getParameterTypes()[0].isAssignableFrom(entity.getClass()) &&
                method.getParameterTypes()[1].isAssignableFrom(context.getClass())) {

            final Object[] args = { entity, context };

            ReflectionUtils.makeAccessible(method);

            final Object target = applicationContext.getBean(beanName);
            ReflectionUtils.invokeMethod(method, target, args);
        }
    }
}
