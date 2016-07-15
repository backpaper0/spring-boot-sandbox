package sample.factorymethod;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class BarFactory {

    @Bean
    Bar bar() {
        return new Bar() {

            @Override
            public String get() {
                return getClass().toString();
            }
        };
    }
}
