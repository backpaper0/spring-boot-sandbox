package com.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

@SpringBootTest
@AutoConfigureMockMvc
public class ExampleControllerTest {

    @Autowired
    private MockMvcTester tester;

    @Test
    void testNoParames() {
        tester.get()
                .uri("/list")
                .assertThat()
                .bodyJson()
                .hasPathSatisfying("offset", a -> a.assertThat().isEqualTo(0))
                .hasPathSatisfying("pageNumber", a -> a.assertThat().isEqualTo(0))
                .hasPathSatisfying("pageSize", a -> a.assertThat().isEqualTo(50))
                .hasPathSatisfying("sort", a -> a.assertThat().isEqualTo(""));
    }

    @ParameterizedTest
    @CsvSource(
            delimiter = '|',
            nullValues = "N/A",
            value = {
                "   2 | N/A | N/A | 100 |   2 |  50 | ''",
                " N/A |  60 | N/A |   0 |   0 |  60 | ''",
                " N/A | N/A | foo |   0 |   0 |  50 | foo ASC ",
                " N/A | 999 | N/A |   0 |   0 | 100 | ''",
                "   3 |  70 | bar | 210 |   3 |  70 | bar ASC ",
            })
    void testPaging(
            Integer page,
            Integer size,
            String sort,
            int expectedOffset,
            int expectedPageNumber,
            int expectedPageSize,
            String expectedSort) {
        var builder = tester.get().uri("/list");
        if (page != null) {
            builder = builder.queryParam("page", page.toString());
        }
        if (size != null) {
            builder = builder.queryParam("size", size.toString());
        }
        if (sort != null) {
            builder = builder.queryParam("sort", sort);
        }
        builder.assertThat()
                .bodyJson()
                .hasPathSatisfying("offset", a -> a.assertThat().isEqualTo(expectedOffset))
                .hasPathSatisfying("pageNumber", a -> a.assertThat().isEqualTo(expectedPageNumber))
                .hasPathSatisfying("pageSize", a -> a.assertThat().isEqualTo(expectedPageSize))
                .hasPathSatisfying("sort", a -> a.assertThat().isEqualTo(expectedSort));
    }
}
