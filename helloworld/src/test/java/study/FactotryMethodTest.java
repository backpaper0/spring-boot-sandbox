package study;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

public class FactotryMethodTest {

    @Test
    public void test() throws Exception {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
            context.register(FooFactory.class);
            context.refresh();

            Foo bean = context.getBean(Foo.class);
            assertThat(bean, not(nullValue()));
        }
    }

    public static class Foo {
    }

    public static class FooFactory {
        @Bean
        Foo foo() {
            //            new Throwable().printStackTrace(System.out);
            return new Foo();
        }
    }
}
