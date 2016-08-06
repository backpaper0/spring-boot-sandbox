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
        Sample sample = new Sample();
        sample.text = text;
        sample.threadName = Thread.currentThread().getName();
        template.convertAndSend("sample", sample);
    }

    @JmsListener(destination = "sample")
    void handle(Sample sample) {
        System.out.println(sample.text);
        System.out.println(sample.threadName);
        System.out.println(Thread.currentThread().getName());
    }
}
