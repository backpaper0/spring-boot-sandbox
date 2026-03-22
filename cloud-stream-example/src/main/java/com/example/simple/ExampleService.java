package com.example.simple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
public class ExampleService {

    @Autowired
    private StreamBridge streamBridge;

    public void greeting() {
        streamBridge.send("greeting", "Hi!");
    }
}
