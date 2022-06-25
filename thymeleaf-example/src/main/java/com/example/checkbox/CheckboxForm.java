package com.example.checkbox;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckboxForm {

	private Boolean singleCheckbox;

	private String[] multiCheckboxes;
}
