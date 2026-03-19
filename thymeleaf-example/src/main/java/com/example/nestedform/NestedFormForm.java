package com.example.nestedform;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NestedFormForm {

    @Valid
    private List<SubForm> subForms = new ArrayList<>();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SubForm {

        @Size(max = 3)
        private String foo;

        @Size(max = 3)
        private String bar;
    }
}
