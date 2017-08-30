package study.runner.order;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;

@SpringBootApplication
public class CommandLineRunnerOrderSample {

    public static void main(final String[] args) {
        SpringApplication.run(CommandLineRunnerOrderSample.class, args);
    }

    @Bean
    ValuesHolder valuesHolder() {
        return new ValuesHolder();
    }

    @Bean
    Runner runner1() {
        return new Runner(valuesHolder(), "qux", 4);
    }

    @Bean
    Runner runner2() {
        return new Runner(valuesHolder(), "bar", 2);
    }

    @Bean
    Runner runner3() {
        return new Runner(valuesHolder(), "baz", 3);
    }

    @Bean
    Runner runner4() {
        return new Runner(valuesHolder(), "foo", 1);
    }
}

class Runner implements CommandLineRunner, Ordered {

    private final ValuesHolder valuesHolder;
    private final String value;
    private final int order;

    public Runner(final ValuesHolder valuesHolder, final String value, final int order) {
        this.valuesHolder = Objects.requireNonNull(valuesHolder);
        this.value = Objects.requireNonNull(value);
        this.order = order;
    }

    @Override
    public void run(final String... args) throws Exception {
        valuesHolder.add(value);
    }

    @Override
    public int getOrder() {
        return order;
    }
}

class ValuesHolder {

    private final List<String> values = new CopyOnWriteArrayList<>();

    public void add(final String value) {
        values.add(value);
    }

    public List<String> get() {
        return values;
    }
}