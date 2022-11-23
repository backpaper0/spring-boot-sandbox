package study.json.immutable;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonProperty;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@RestController
public class ImmutableJsonSample {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		SpringApplication.run(ImmutableJsonSample.class, args);
	}

	@PostMapping("/emp")
	Emp get(@RequestBody final Emp emp) {
		return emp;
	}
}

final class Emp {

	public final Integer id;
	public final String name;
	public final Dept dept;

	public Emp(
			@JsonProperty("id") final Integer id,
			@JsonProperty("name") final String name,
			@JsonProperty("dept") final Dept dept) {
		this.id = id;
		this.name = name;
		this.dept = dept;
	}
}

final class Dept {

	public final Integer id;
	public final String name;

	public Dept(
			@JsonProperty("id") final Integer id,
			@JsonProperty("name") final String name) {
		this.id = id;
		this.name = name;
	}
}
