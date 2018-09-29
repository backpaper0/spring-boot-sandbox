package com.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.r2dbc.client.R2dbc;
import io.r2dbc.spi.ConnectionFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final ConnectionFactory connectionFactory;
    private final R2dbc r2dbc;
    private final MessageMapper mapper = new MessageMapper();

    public MessageController(final ConnectionFactory connectionFactory, final R2dbc r2dbc) {
        this.connectionFactory = connectionFactory;
        this.r2dbc = r2dbc;
    }

    @GetMapping
    public Flux<Message> getAll() {
        return Mono.from(connectionFactory.create())
                .flatMapMany(con -> con
                        .createStatement("SELECT id, text FROM messages ORDER BY id")
                        .execute())
                .flatMap(result -> result.map((row, meta) -> mapper.map(row)));
    }

    @GetMapping("/by_client")
    public Flux<Message> getAllByClient() {
        return r2dbc.withHandle(handle -> handle
                .select("SELECT id, text FROM messages ORDER BY id")
                .mapRow(mapper::map));
    }

    @PostMapping("/by_client")
    public Mono<Void> postByClient(@RequestBody final Text message) {
        return r2dbc.useTransaction(handle -> handle
                .execute("INSERT INTO messages (text) VALUES ($1)", message.getText()));
    }

    static final class Text {

        private final String text;

        public Text(@JsonProperty("text") final String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }
}
