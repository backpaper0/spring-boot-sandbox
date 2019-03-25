package study.event.sample2;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.BeansException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.context.event.EventListenerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

@SpringBootApplication
public class EventSample2 implements CommandLineRunner {

    public static void main(final String[] args) {
        final SpringApplication sa = new SpringApplication(EventSample2.class);
        sa.setWebApplicationType(WebApplicationType.NONE);
        sa.run(args);
    }

    private final ApplicationEventPublisher publisher;

    public EventSample2(final ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void run(final String... args) throws Exception {
        System.out.printf("publish start: %s%n", SampleEvent.FOO);
        publisher.publishEvent(new CustomApplicationEvent(SampleEvent.FOO));
        System.out.printf("publish end: %s%n", SampleEvent.FOO);
        System.out.printf("publish start: %s%n", SampleEvent.BAR);
        publisher.publishEvent(new CustomApplicationEvent(SampleEvent.BAR));
        System.out.printf("publish end: %s%n", SampleEvent.BAR);
    }

    @FooListener
    void handleFoo(final SampleEvent event) {
        System.out.printf("handleFoo: %s%n", event);
    }

    @BarListener
    void handleBar(final SampleEvent event) {
        System.out.printf("handleBar: %s%n", event);
    }

    @BothListener
    void handleBoth(final SampleEvent event) {
        System.out.printf("handleBoth: %s%n", event);
    }
}

enum SampleEvent {
    FOO, BAR
}

@EventListener
@Retention(RetentionPolicy.RUNTIME)
@interface FooListener {
}

@EventListener
@Retention(RetentionPolicy.RUNTIME)
@interface BarListener {
}

@EventListener
@Retention(RetentionPolicy.RUNTIME)
@interface BothListener {
}

@Component
class CustomEventListenerFactory implements EventListenerFactory, Ordered, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    @Override
    public boolean supportsMethod(final Method method) {
        return method.isAnnotationPresent(FooListener.class) ||
                method.isAnnotationPresent(BarListener.class) ||
                method.isAnnotationPresent(BothListener.class);
    }

    @Override
    public ApplicationListener<?> createApplicationListener(final String beanName,
            final Class<?> type,
            final Method method) {
        if (method.isAnnotationPresent(FooListener.class)) {
            return new CustomApplicationListener(applicationContext, beanName, method,
                    SampleEvent.FOO);
        }
        if (method.isAnnotationPresent(BarListener.class)) {
            return new CustomApplicationListener(applicationContext, beanName, method,
                    SampleEvent.BAR);
        }
        return new CustomApplicationListener(applicationContext, beanName, method,
                SampleEvent.values());
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }
}

class CustomApplicationEvent extends ApplicationEvent {

    public CustomApplicationEvent(final SampleEvent source) {
        super(source);
    }
}

class CustomApplicationListener implements ApplicationListener<CustomApplicationEvent> {

    private final ApplicationContext applicationContext;
    private final String beanName;
    private final Method method;
    private final Collection<SampleEvent> targets;

    public CustomApplicationListener(final ApplicationContext applicationContext,
            final String beanName,
            final Method method, final SampleEvent... targets) {
        this.applicationContext = applicationContext;
        this.beanName = beanName;
        this.method = method;
        this.targets = Arrays.asList(targets);
    }

    @Override
    public void onApplicationEvent(final CustomApplicationEvent event) {
        final Object source = event.getSource();
        if (targets.contains(source)) {
            final Object bean = applicationContext.getBean(beanName);
            ReflectionUtils.makeAccessible(method);
            ReflectionUtils.invokeMethod(method, bean, source);
        }
    }
}