package com.example;

import java.net.URI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class UIApplication {

    public static void main(String[] args) {
        SpringApplication.run(UIApplication.class, args);
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

@Controller
class HomeController {

    final RestTemplate restTemplate;

    @Value("${backend-service.api}")
    URI uri;

    public HomeController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/")
    String home(Model model) {
        String hello = restTemplate.getForObject(uri, String.class);
        model.addAttribute("hello", hello);
        return "home";
    }
}
