package sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleContoller {

    @Autowired
    JmsTemplate template;

    @PostMapping
    void post(@RequestParam String text) {
        template.convertAndSend("sample", text + ":" + Thread.currentThread().getName());
    }

    @JmsListener(destination = "sample")
    void handle(String text) {
        System.out.println(text + ":" + Thread.currentThread().getName());
    }
}
