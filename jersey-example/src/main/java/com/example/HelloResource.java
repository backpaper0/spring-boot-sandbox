package com.example;

import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

@Component
@Path("/hello")
public class HelloResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Object get() {
		return Map.of("message", "Hello World");
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/date/{year:\\d{4}}")
	public Object regex(@PathParam("year") int year) {
		return Map.of("year", year);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/size/{x}x{y}")
	public Object world(@PathParam("x") int x, @PathParam("y") int y) {
		return Map.of("x", x, "y", y);
	}
}
