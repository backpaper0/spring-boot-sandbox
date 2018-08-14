package study.requestscope;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = RequestScopeExample.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class RequestScopeExampleTest {

    @Test
    public void test() throws Exception {
    }
}
