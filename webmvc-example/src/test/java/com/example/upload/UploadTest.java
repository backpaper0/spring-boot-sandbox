package com.example.upload;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
public class UploadTest {

	private MockMvc mockMvc;

	@BeforeEach
	void init(WebApplicationContext wac) {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	void uploadSingleFile() throws Exception {
		mockMvc.perform(multipart("/upload")
				.file(new MockMultipartFile("file", "test.txt", "text/plain", "test".getBytes()))
				.param("singleFile", ""))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/upload"))
				.andExpect(flash().attribute("message", "単一ファイルのアップロード"))
				.andExpect(flash().attribute("uploadedFiles", List.of("test.txt")));
	}

	@Test
	void uploadMultiFile() throws Exception {
		mockMvc.perform(multipart("/upload")
				.file(new MockMultipartFile("files", "test1.txt", "text/plain", "test 1".getBytes()))
				.file(new MockMultipartFile("files", "test2.txt", "text/plain", "test 2".getBytes()))
				.file(new MockMultipartFile("files", "test3.txt", "text/plain", "test 3".getBytes()))
				.param("multiFiles", ""))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/upload"))
				.andExpect(flash().attribute("message", "複数ファイルのアップロード"))
				.andExpect(flash().attribute("uploadedFiles", List.of("test1.txt", "test2.txt", "test3.txt")));
	}
}
