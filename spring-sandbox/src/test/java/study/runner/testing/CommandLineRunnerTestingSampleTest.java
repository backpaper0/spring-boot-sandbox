package study.runner.testing;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(
        webEnvironment = WebEnvironment.NONE,
        properties = "spring.profiles.active=test")
class CommandLineRunnerTestingSampleTest {

    @Test
    void test() {
        //Profileを利用してCommandLineRunnerをコンポーネント登録しない
    }
}
