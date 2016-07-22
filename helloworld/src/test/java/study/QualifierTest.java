package study;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class QualifierTest {

    @Test
    public void test() throws Exception {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
            context.register(Foo.class, Bar1.class, Bar2.class);
            context.refresh();

            Foo bean = context.getBean(Foo.class);
            System.out.println(bean);
            System.out.println(bean.bar1);
            System.out.println(bean.bar2);
            assertThat(bean.bar1, instanceOf(Bar1.class));
            assertThat(bean.bar2, instanceOf(Bar2.class));
        }
    }

    public static class Foo {

        @Autowired
        @Qualifier("1")
        Bar bar1;

        @Autowired
        @Qualifier("2")
        Bar bar2;
    }

    public interface Bar {
    }

    @Qualifier("1")
    public static class Bar1 implements Bar {
    }

    @Qualifier("2")
    public static class Bar2 implements Bar {
    }
}
