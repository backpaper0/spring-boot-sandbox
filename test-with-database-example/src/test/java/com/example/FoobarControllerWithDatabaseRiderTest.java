package com.example;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.junit5.api.DBRider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@DBRider
public class FoobarControllerWithDatabaseRiderTest {

    MockMvc mvc;

    @Autowired
    WebApplicationContext wac;

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    @DataSet("foobar.yml")
    @ExpectedDataSet("expectedFoobar.yml")
    void test() throws Exception {
        mvc.perform(get("/foobar"))
                .andExpectAll(
                        status().isOk(),
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
