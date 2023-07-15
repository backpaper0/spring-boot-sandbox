package com.example;

import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.cassandra.core.CassandraAdminTemplate;

import com.datastax.oss.driver.api.core.CqlIdentifier;

@SpringBootApplication
public class App implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	private final Logger logger = LoggerFactory.getLogger(App.class);

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CassandraAdminTemplate template;

	@Override
	public void run(ApplicationArguments args) throws Exception {

		template.createTable(true, CqlIdentifier.fromCql("user"), User.class, Map.of());

		User user1 = new User();
		user1.setId(UUID.fromString("a07187a1-de8f-464b-b83d-14d66774b099"));
		user1.setName("Alice");
		userRepository.save(user1);

		User user2 = new User();
		user2.setId(UUID.fromString("547d5221-40e8-4b9e-a004-1307242c65cc"));
		user2.setName("Bob");
		userRepository.save(user2);

		Iterable<User> users = userRepository.findAll();
		for (User user : users) {
			logger.info("{}", user);
		}
	}

}
