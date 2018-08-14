package study.event.sample3;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

@SpringBootApplication
public class EventSample3 implements CommandLineRunner {

    public static void main(final String[] args) {
        final SpringApplication sa = new SpringApplication(EventSample3.class);
        sa.setWebApplicationType(WebApplicationType.NONE);
        sa.run(args);
    }

    @Autowired
    ApplicationEventPublisher publisher;

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

    @EventListener
    void handle(final CustomApplicationEvent event) {
        System.out.printf("handle: %s%n", event);
    }

}

enum SampleEvent {
    FOO, BAR
}

@EventListener
@Retention(RetentionPolicy.RUNTIME)
@interface CustomListener {
    SampleEvent[] value();
}

@CustomListener(SampleEvent.FOO)
@Retention(RetentionPolicy.RUNTIME)
@interface FooListener {
}

@CustomListener(SampleEvent.BAR)
@Retention(RetentionPolicy.RUNTIME)
@interface BarListener {
}

@CustomListener({ SampleEvent.FOO, SampleEvent.BAR })
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
        return AnnotationUtils.findAnnotation(method, CustomListener.class) != null;
    }

    @Override
    public ApplicationListener<?> createApplicationListener(final String beanName,
            final Class<?> type,
            final Method method) {
        final SampleEvent[] values = AnnotationUtils.findAnnotation(method, CustomListener.class)
                .value();
        return new CustomApplicationListener(applicationContext, beanName, method, values);
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