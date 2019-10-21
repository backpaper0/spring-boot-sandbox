package study.requestscope;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(classes = RequestScopeExample.class, webEnvironment = WebEnvironment.RANDOM_PORT)
class RequestScopeExampleTest {

    @Test
    void test() throws Exception {
    }
}
