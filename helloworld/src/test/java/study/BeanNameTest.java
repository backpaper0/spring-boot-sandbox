package study;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

class BeanNameTest {

    @Test
    void test() throws Exception {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
            context.register(Foo.class, Bar.class, Factory.class);
            context.refresh();

            final String[] fooNames = context.getBeanNamesForType(Foo.class);
            assertThat(fooNames.length, is(1));
            assertThat(fooNames[0], is("beanNameTest.Foo"));

            final String[] barNames = context.getBeanNamesForType(Bar.class);
            assertThat(barNames.length, is(1));
            assertThat(barNames[0], is("bbaarr"));

            final String[] bazNames = context.getBeanNamesForType(Baz.class);
            assertThat(bazNames.length, is(1));
            assertThat(bazNames[0], is("createBaz"));

            final String[] quxNames = context.getBeanNamesForType(Qux.class);
            assertThat(quxNames.length, is(1));
            assertThat(quxNames[0], is("qquuxx"));
        }
    }

    //何もなければ単純名がコンポーネント名になるっぽい
    public static class Foo {
    }

    @Component("bbaarr")
    public static class Bar {
    }

    @Configuration
    public static class Factory {
        //メソッド名がコンポーネント名になるっぽい
        @Bean
        public Baz createBaz() {
            return new Baz();
        }

        @Bean(name = "qquuxx")
        public Qux createQux() {
            return new Qux();
        }
    }

    public static class Baz {
    }

    public static class Qux {
    }
}
