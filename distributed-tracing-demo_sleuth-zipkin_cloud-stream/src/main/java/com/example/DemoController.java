package com.example;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

	private final StreamBridge streamBridge;

	public DemoController(StreamBridge streamBridge) {
		this.streamBridge = streamBridge;
	}

	@PostMapping("/")
	public String post(@RequestParam(defaultValue = "helloworld") String text) {
		streamBridge.send("demo1", text);
		return "OK";
	}
}
