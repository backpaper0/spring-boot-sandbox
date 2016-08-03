package sample;

import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    private final MessageRepository repository;
    private final UUID id = UUID.randomUUID();

    public MessageController(MessageRepository template) {
        this.repository = template;
    }

    @GetMapping
    Message get() {
        return repository.findOne(id);
    }

    @PostMapping
    void post(@RequestParam String text) {
        repository.save(new Message(id, text));
    }
}
