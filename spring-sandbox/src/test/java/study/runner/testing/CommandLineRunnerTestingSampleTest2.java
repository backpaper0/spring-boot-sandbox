package study.runner.testing;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Tests
@ExtendWith(SpringExtension.class)
public class CommandLineRunnerTestingSampleTest2 {

    @Test
    public void test() {
        //Profileを利用してCommandLineRunnerをコンポーネント登録しない
    }
}
