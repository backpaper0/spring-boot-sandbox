package com.example;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.function.DoubleConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@EnableAsync
@SpringBootApplication
public class ProgressSampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProgressSampleApplication.class, args);
	}
}

@Controller
class SampleController {

	@Autowired
	SampleService service;
	@Autowired
	Progress progress;

	@GetMapping("/")
	String index() {
		return "index";
	}

	@PostMapping("/run")
	@ResponseBody
	String run() {
		UUID key = UUID.randomUUID();
		service.post(a -> progress.getListener(key).accept(a));
		return key.toString();
	}

	@GetMapping("/progress/{name}")
	SseEmitter progress(@PathVariable String name) {
		UUID key = UUID.fromString(name);
		SseEmitter emitter = new SseEmitter();
		progress.addListener(key, a -> {
			try {
				emitter.send(a);
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
			if (a >= 1) {
				emitter.complete();
			}
		});
		emitter.onCompletion(() -> progress.removeListener(key));
		return emitter;
	}
}

@Component
class Progress {
	final ConcurrentMap<UUID, DoubleConsumer> listeners = new ConcurrentHashMap<>();

	void addListener(UUID key, DoubleConsumer listener) {
		listeners.put(key, listener);
	}

	void removeListener(UUID key) {
		listeners.remove(key);
	}

	DoubleConsumer getListener(UUID key) {
		return listeners.getOrDefault(key, a -> {
		});
	}
}

@Service
class SampleService {
	@Async
	public void post(DoubleConsumer consumer) {
		Random r = new Random();
		for (double d = 0; d < 1.0; d += r.nextDouble() * 0.1) {
			consumer.accept(d);
			try {
				TimeUnit.MILLISECONDS.sleep(r.nextInt(1000));
			} catch (InterruptedException e) {
			}
		}
		consumer.accept(1);
	}
}
