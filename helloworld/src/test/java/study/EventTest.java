package study;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.event.EventListener;

public class EventTest {

    @Test
    public void test() throws Exception {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
            context.register(Foo.class);
            context.refresh();

            Foo bean = context.getBean(Foo.class);

            FooEvent event = new FooEvent();
            context.publishEvent(event);

            assertThat(bean.event, sameInstance(event));
        }
    }

    public static class Foo {

        FooEvent event;

        @EventListener
        public void handle(FooEvent event) {
            this.event = event;
        }
    }

    public static class FooEvent {
    }
}
