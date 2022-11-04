package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CacheExampleApplication implements CommandLineRunner {

	@Autowired
	private CacheExample example;

	public static void main(final String[] args) {
		SpringApplication.run(CacheExampleApplication.class, args);
	}

	@Override
	public void run(final String... args) throws Exception {
		System.out.println(example.foo(1));
		System.out.println(example.foo(2));

		//keyがfoo(1)と被っているからキャッシュされているfoo:1が表示される
		System.out.println(example.bar(1));

		System.out.println(example.bar(3));
	}
}
