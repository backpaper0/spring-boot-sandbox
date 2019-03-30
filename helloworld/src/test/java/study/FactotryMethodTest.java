package study;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

class FactotryMethodTest {

    @Test
    void test() throws Exception {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
            context.register(FooFactory.class);
            context.refresh();

            final Foo bean = context.getBean(Foo.class);
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
