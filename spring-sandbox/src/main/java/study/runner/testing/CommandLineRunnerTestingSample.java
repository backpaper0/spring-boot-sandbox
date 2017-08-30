package study.runner.testing;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/*
 * CommandLineRunnerをテストすると言うよりもテストを考慮したクラス設計というか。 
 *
 */
@SpringBootApplication
public class CommandLineRunnerTestingSample {
    public static void main(final String[] args) {
        final SpringApplication sa = new SpringApplication(CommandLineRunnerTestingSample.class);
        sa.setWebEnvironment(false);
        sa.run(args);
    }
}

@Profile("!test")
@Component
class Runner implements CommandLineRunner {
    @Override
    public void run(final String... args) throws Exception {
        throw new Exception();
    }
}
