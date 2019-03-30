package study;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.event.EventListener;

class EventTest {

    @Test
    void test() throws Exception {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
            context.register(Foo.class);
            context.refresh();

            final Foo bean = context.getBean(Foo.class);

            final FooEvent event = new FooEvent();
            context.publishEvent(event);

            assertThat(bean.event, sameInstance(event));
        }
    }

    public static class Foo {

        FooEvent event;

        @EventListener
        public void handle(final FooEvent event) {
            this.event = event;
        }
    }

    public static class FooEvent {
    }
}
