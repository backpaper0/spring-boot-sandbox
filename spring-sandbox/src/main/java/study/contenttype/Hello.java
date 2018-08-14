package study.contenttype;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class Hello {
    @GetMapping(produces = { "text/json", "application/json" })
    public Message get() {
        return new Message("Hello, world!");
    }

    public static class Message {
        public final String text;

        public Message(final String text) {
            this.text = text;
        }
    }
}
