package sample;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    private final MessageRepository repository;

    public MessageController(MessageRepository template) {
        this.repository = template;
    }

    @GetMapping
    Message get() {
        return repository.findOne("foobar");
    }

    @PostMapping
    void post(@RequestParam String text) {
        repository.save(new Message("foobar", text));
    }
}
