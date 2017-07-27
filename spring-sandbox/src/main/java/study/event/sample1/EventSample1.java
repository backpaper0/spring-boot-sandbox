package study.event.sample1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class EventSample1 implements CommandLineRunner {

    public static void main(final String[] args) {
        final SpringApplication sa = new SpringApplication(EventSample1.class);
        sa.setWebEnvironment(false);
        sa.run(args);
    }

    @Autowired
    ApplicationEventPublisher publisher;

    @Override
    public void run(final String... args) throws Exception {
        final SampleEvent event = new SampleEvent();
        System.out.printf("publish start: %s%n", event);
        publisher.publishEvent(event);
        System.out.printf("publish end: %s%n", event);
    }

    @EventListener
    void handle(final SampleEvent event) {
        System.out.printf("handle: %s%n", event);
    }
}

class SampleEvent {

}