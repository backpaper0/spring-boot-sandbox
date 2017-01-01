package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@EnableOAuth2Sso
public class HelloWorldUIApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloWorldUIApplication.class, args);
    }

    @Bean
    OAuth2RestTemplate restTemplate(OAuth2ProtectedResourceDetails resource,
            OAuth2ClientContext context) {
        return new OAuth2RestTemplate(resource, context);
    }
}

@Controller
class HelloController {

    final OAuth2RestTemplate restTemplate;

    public HelloController(OAuth2RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/")
    String home() {
        return "redirect:/hello-world";
    }

    @GetMapping("/hello-world")
    String helloWorld(Model model) {
        model.addAttribute("hello",
                restTemplate.getForObject("http://localhost:8000/hello-world", String.class));
        return "hello-world";
    }

    @GetMapping("/hello-user")
    String helloUser(Model model) {
        model.addAttribute("hello",
                restTemplate.getForObject("http://localhost:8000/hello-user", String.class));
        return "hello-user";
    }
}