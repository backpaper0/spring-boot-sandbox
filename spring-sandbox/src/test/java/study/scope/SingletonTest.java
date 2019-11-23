package study.scope;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

class SingletonTest {

    @Test
    void test() throws Exception {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
            context.register(Foo.class);
            context.register(Bar.class);
            context.register(Hoge.class);
            context.refresh();

            final Bar bar = context.getBean(Bar.class);
            final Hoge hoge = context.getBean(Hoge.class);
            assertTrue(bar.getValue().equals(hoge.getValue()));
        }
    }

    static class Foo {

        private final String value = UUID.randomUUID().toString();

        public String getValue() {
            return value;
        }
    }

    static class Bar {

        private final Foo foo;

        public Bar(final Foo foo) {
            this.foo = foo;
        }

        public String getValue() {
            return foo.getValue();
        }
    }

    static class Hoge {

        private final Foo foo;

        public Hoge(final Foo foo) {
            this.foo = foo;
        }

        public String getValue() {
            return foo.getValue();
        }
    }
}
