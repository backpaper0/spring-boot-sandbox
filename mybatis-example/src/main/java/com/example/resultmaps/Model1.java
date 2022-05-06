package com.example.resultmaps;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Model1 {

	private Integer id;
	private String name;
	private Model2 model2;
	private List<Model3> model3List;
}
