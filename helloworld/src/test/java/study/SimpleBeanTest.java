package study;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SimpleBeanTest {

    @Test
    public void test() throws Exception {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
            context.register(FooImpl.class);
            context.refresh();

            Foo bean = context.getBean(Foo.class);
            System.out.println(bean);
            assertThat(bean, instanceOf(FooImpl.class));
        }
    }

    public interface Foo {
    }

    public static class FooImpl implements Foo {
    }
}
