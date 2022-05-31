package com.example;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.junit5.api.DBRider;

@SpringBootTest
@AutoConfigureMockMvc
@DBRider
public class FoobarControllerWithDatabaseRiderTest {

	@Autowired
	MockMvc mvc;

	@Test
	@DataSet("foobar.yml")
	@ExpectedDataSet("expectedFoobar.yml")
	void test() throws Exception {
		mvc.perform(get("/foobar"))
				.andExpectAll(status().isOk(),
						jsonPath("$[0].id").isNumber(),
						jsonPath("$[0].content").value("foo"),
						jsonPath("$[1].id").isNumber(),
						jsonPath("$[1].content").value("bar"),
						jsonPath("$[2].id").isNumber(),
						jsonPath("$[2].content").value("baz"),
						jsonPath("$[3].id").isNumber(),
						jsonPath("$[3].content").value("qux"));
	}
}
