package study.factorybean;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(classes = FactoryBeanExample.class, webEnvironment = WebEnvironment.NONE)
class FactoryBeanExampleTest {

    @Autowired
    private FooService service;

    @Test
    void run() {
        service.run();
    }
}
