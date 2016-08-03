package sample;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, UUID> {
}
