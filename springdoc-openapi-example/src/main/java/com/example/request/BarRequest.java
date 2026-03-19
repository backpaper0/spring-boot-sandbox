package com.example.request;

import java.time.LocalDate;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class BarRequest {

    private String param1;

    private Integer param2;

    private boolean param3;

    @DateTimeFormat(pattern = "uuuu-MM-dd")
    private LocalDate param4;
}
