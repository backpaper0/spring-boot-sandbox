package com.example;

import com.example.PersonHandlers.Person;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

class PersonHandlersTest {

    WebTestClient client =
            WebTestClient.bindToRouterFunction(PersonHandlers.routerFunction()).build();

    @Test
    void getPerson() {
        client.get()
                .uri("/people/hoge")
                .exchange()
                .expectHeader()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("name")
                .isEqualTo("hoge");
    }

    @Test
    void postPerson() {
        client.post()
                .uri("/people")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new Person("foobar"))
                .exchange()
                .expectHeader()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("name")
                .isEqualTo("foobar");
    }
}
