package com.example;

import org.springframework.data.annotation.Id;

public record Foobar(@Id Integer id, String content) {
}
