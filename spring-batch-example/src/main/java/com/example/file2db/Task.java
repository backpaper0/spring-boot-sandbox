package com.example.file2db;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Task {

	private Integer id;
	@NotNull
	@Size(min = 1)
	private String content;
	private boolean done;

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

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}
}
