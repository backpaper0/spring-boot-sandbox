package study;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

class SimpleBeanTest {

    @Test
    void test() throws Exception {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
            context.register(FooImpl.class);
            context.refresh();

            final Foo bean = context.getBean(Foo.class);
            System.out.println(bean);
            assertThat(bean, instanceOf(FooImpl.class));
        }
    }

    public interface Foo {
    }

    public static class FooImpl implements Foo {
    }
}
