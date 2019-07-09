package study.bean;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

class BeanTest {

    @Test
    void test() throws Exception {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
            context.register(Ddd.class);
            context.refresh();

            final Aaa aaa = context.getBean(Aaa.class);
            final Bbb bbb = context.getBean(Bbb.class);
            final Ccc ccc = context.getBean(Ccc.class);
            final Ddd ddd = context.getBean(Ddd.class);

            assertNotNull(aaa);
            assertNotNull(bbb);
            assertNotNull(ccc);
            assertNotNull(ddd);

            assertTrue(aaa == bbb);
            assertTrue(aaa == ccc);
            assertTrue(aaa == ddd);
        }
    }

    interface Aaa {
    }

    interface Bbb extends Aaa {
    }

    static class Ccc implements Bbb {
    }

    static class Ddd extends Ccc {
    }

    @Test
    void testQualifier() throws Exception {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
            context.register(Ggg.class);
            context.register(Hhh.class);
            context.register(Iii.class);
            context.refresh();

            final Hhh hhh = context.getBean(Hhh.class);

            assertNotNull(hhh.aaa);
            assertNotNull(hhh.bbb);
            assertNotNull(hhh.ccc);
            assertNotNull(hhh.ddd);

            assertTrue(hhh.aaa == hhh.bbb);
            assertTrue(hhh.aaa == hhh.ccc);
            assertTrue(hhh.aaa == hhh.ddd);

            final Iii iii = context.getBean(Iii.class);

            assertNotNull(iii.aaa);
            assertNotNull(iii.bbb);
            assertNotNull(iii.ccc);
            assertNotNull(iii.ddd);

            assertTrue(iii.aaa == iii.bbb);
            assertTrue(iii.aaa == iii.ccc);
            assertTrue(iii.aaa == iii.ddd);

            assertTrue(hhh.aaa != iii.aaa);
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Qualifier
    @interface Eee {
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Qualifier
    @interface Fff {
    }

    static class Ggg {

        @Bean
        @Eee
        Ddd ggg() {
            return new Ddd();
        }

        @Bean
        @Fff
        Ddd fff() {
            return new Ddd();
        }
    }

    static class Hhh {

        Aaa aaa;
        Bbb bbb;
        Ccc ccc;
        Ddd ddd;

        Hhh(@Eee final Aaa aaa, @Eee final Bbb bbb, @Eee final Ccc ccc, @Eee final Ddd ddd) {
            this.aaa = aaa;
            this.bbb = bbb;
            this.ccc = ccc;
            this.ddd = ddd;
        }
    }

    static class Iii {

        Aaa aaa;
        Bbb bbb;
        Ccc ccc;
        Ddd ddd;

        Iii(@Fff final Aaa aaa, @Fff final Bbb bbb, @Fff final Ccc ccc, @Fff final Ddd ddd) {
            this.aaa = aaa;
            this.bbb = bbb;
            this.ccc = ccc;
            this.ddd = ddd;
        }
    }
}
