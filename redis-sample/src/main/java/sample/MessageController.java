package sample;

import java.util.Optional;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

	private final MessageRepository repository;
	private final UUID id = UUID.randomUUID();

	public MessageController(final MessageRepository template) {
		this.repository = template;
	}

	@GetMapping
	Optional<Message> get() {
		return repository.findById(id);
	}

	@PostMapping
	void post(@RequestParam final String text) {
		repository.save(new Message(id, text));
	}
}
