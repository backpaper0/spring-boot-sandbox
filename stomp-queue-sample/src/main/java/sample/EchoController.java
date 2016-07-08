package sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class EchoController {

    private final SimpMessagingTemplate template;

    @Autowired
    public EchoController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping("/queue/echo")
    public String echo(String message) {
        template.convertAndSend("/queue/response", message);
        return "OK";
    }
}
