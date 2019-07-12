package study.scope;

import java.util.Objects;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoController {

    private Counter counter;

    public DemoController(Counter counter) {
        this.counter = Objects.requireNonNull(counter);
    }

    @GetMapping
    public int getAndIncrement() {
        return counter.getAndIncrement();
    }
}
