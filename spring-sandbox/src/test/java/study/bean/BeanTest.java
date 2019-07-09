package study.bean;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

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
}
