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

    @Test
    void testJpaPaging() {
        tester.get()
                .uri("/examples")
                .queryParam("size", "7")
                .assertThat()
                .bodyJson()
                .hasPathSatisfying("$.content[0].msg", a -> a.assertThat().isEqualTo("a"))
                .hasPathSatisfying("$.content[1].msg", a -> a.assertThat().isEqualTo("b"))
                .hasPathSatisfying("$.content[2].msg", a -> a.assertThat().isEqualTo("c"))
                .hasPathSatisfying("$.content[3].msg", a -> a.assertThat().isEqualTo("d"))
                .hasPathSatisfying("$.content[4].msg", a -> a.assertThat().isEqualTo("e"))
                .hasPathSatisfying("$.content[5].msg", a -> a.assertThat().isEqualTo("f"))
                .hasPathSatisfying("$.content[6].msg", a -> a.assertThat().isEqualTo("g"))
                .hasPathSatisfying("$.page.number", a -> a.assertThat().isEqualTo(0))
                .hasPathSatisfying("$.page.size", a -> a.assertThat().isEqualTo(7))
                .hasPathSatisfying("$.page.totalElements", a -> a.assertThat().isEqualTo(52))
                .hasPathSatisfying("$.page.totalPages", a -> a.assertThat().isEqualTo(8));
    }
}
