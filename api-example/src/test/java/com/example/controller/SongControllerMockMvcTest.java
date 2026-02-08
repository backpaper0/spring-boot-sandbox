package com.example.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class SongControllerMockMvcTest extends RestControllerTestBase {

	@Autowired
	MockMvc mvc;

	@Test
	void testGetList() throws Exception {
		mvc.perform(get("/songs"))
				.andExpectAll(status().isOk(),
						jsonPath("$.songs[0].id").value(1),
						jsonPath("$.songs[0].title").value("Limit"),
						jsonPath("$.songs[0].singer.id").value(1),
						jsonPath("$.songs[0].singer.name").value("LUNA SEA"),

						jsonPath("$.songs[1].id").value(2),
						jsonPath("$.songs[1].title").value("MECHANICAL DANCE"),
						jsonPath("$.songs[1].singer.id").value(1),
						jsonPath("$.songs[1].singer.name").value("LUNA SEA"),

						jsonPath("$.songs[2].id").value(3),
						jsonPath("$.songs[2].title").value("Unlikelihood"),
						jsonPath("$.songs[2].singer.id").value(1),
						jsonPath("$.songs[2].singer.name").value("LUNA SEA"),

						jsonPath("$.songs[3].id").value(4),
						jsonPath("$.songs[3].title").value("デルモ"),
						jsonPath("$.songs[3].singer.id").value(2),
						jsonPath("$.songs[3].singer.name").value("Mr.Children"),

						jsonPath("$.songs[4].id").value(5),
						jsonPath("$.songs[4].title").value("UFO"),
						jsonPath("$.songs[4].singer.id").value(2),
						jsonPath("$.songs[4].singer.name").value("Mr.Children"));
	}

	@Test
	void testGetListWithJSONAssert() throws Exception {
		String response = mvc.perform(get("/songs"))
				.andExpectAll(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString(StandardCharsets.UTF_8);

		JSONAssert.assertEquals(
				read("expected-list.json"),
				response, false);
	}

	@Test
	void testGetListInvalid() throws Exception {
		mvc.perform(get("/songs").queryParam("singer", "0").locale(Locale.JAPANESE))
				.andExpectAll(status().isBadRequest(),
						jsonPath("$.type").value("about:blank"),
						jsonPath("$.title").value("Bad Request"),
						jsonPath("$.status").value(400),
						jsonPath("$.instance").value("/songs"),
						jsonPath("$.globalErrors.length()").value(0),
						jsonPath("$.fieldErrors.length()").value(1),
						jsonPath("$.fieldErrors.singer.length()").value(1),
						jsonPath("$.fieldErrors.singer[0]").value("1 以上の値にしてください"));
	}

	@Test
	void testCreate() throws Exception {
		String response = mvc
				.perform(post("/songs")
						.contentType(MediaType.APPLICATION_JSON)
						.content(read("create.json")))
				.andExpectAll(status().isCreated())
				.andReturn()
				.getResponse()
				.getContentAsString(StandardCharsets.UTF_8);

		JSONAssert.assertEquals(
				read("expected-create.json"),
				response, false);
	}
}
