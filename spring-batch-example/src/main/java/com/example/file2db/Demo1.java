package com.example.file2db;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Demo1 {

	private Integer id;
	@NotNull
	@Size(min = 1)
	private String content;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Demo1 [id=" + id + ", content=" + content + "]";
	}
}
