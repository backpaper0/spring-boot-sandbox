package com.example.message;

import com.example.message.MessageResolveTest.TestController;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@Import(TestController.class)
public class MessageResolveTest {

    private MockMvcTester tester;

    @BeforeEach
    void setUp(WebApplicationContext wac) {
        tester = MockMvcTester.from(wac);
    }

    @Test
    void testMessageResolve() {
        tester.post()
                .uri("/test")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "testField": ""
                    }
                """)
                .assertThat()
                .bodyText()
                .isEqualTo("""
0:
  ARGUMENTS:
    0:
      ARGUMENTS:
      CODES:
        0: testRequest.testField
        1: testField
    1: 10
    2: 1
  CODES:
    0: Size.testRequest.testField
    1: Size.testField
    2: Size.java.lang.String
    3: Size
""");
    }

    @RestController
    public static class TestController {

        @PostMapping(path = "/test", produces = "text/plain")
        String handleTest(@RequestBody @Valid TestRequest request, BindingResult bindingResult) {
            var w = new StringWriter();
            try (var out = new PrintWriter(w)) {
                var i = 0;
                for (var error : bindingResult.getAllErrors()) {
                    out.printf("%s:%n", i);
                    println(out, error, 2, 2);
                    i++;
                }
            }
            return w.toString();
        }

        static void println(PrintWriter out, MessageSourceResolvable msr, int indent, int indentAug) {
            var indent1 = IntStream.range(0, indent).mapToObj(i -> " ").collect(Collectors.joining());
            var indent2 =
                    IntStream.range(0, indent + indentAug).mapToObj(i -> " ").collect(Collectors.joining());
            out.printf("%sARGUMENTS:%n", indent1);
            var j = 0;
            if (msr.getArguments() != null) {
                for (var arg : msr.getArguments()) {
                    if (arg instanceof MessageSourceResolvable msr2) {
                        out.printf("%s%s:%n", indent2, j);
                        println(out, msr2, indent + indentAug + indentAug, indentAug);
                    } else {
                        out.printf("%s%s: %s%n", indent2, j, arg);
                    }
                    j++;
                }
            }
            out.printf("%sCODES:%n", indent1);
            var k = 0;
            for (var code : msr.getCodes()) {
                out.printf("%s%s: %s%n", indent2, k, code);
                k++;
            }
        }
    }

    public static class TestRequest {
        @Size(min = 1, max = 10)
        private String testField;

        public String getTestField() {
            return testField;
        }

        public void setTestField(String testField) {
            this.testField = testField;
        }
    }
}
