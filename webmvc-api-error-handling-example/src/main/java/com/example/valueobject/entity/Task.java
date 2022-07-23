package com.example.valueobject.entity;

import com.example.valueobject.value.TaskContent;
import com.example.valueobject.value.TaskId;
import com.example.valueobject.value.TaskStatus;

public class Task {

	private final TaskId id;
	private final TaskContent content;
	private final TaskStatus status;

	public Task(TaskId id, TaskContent content, TaskStatus status) {
		this.id = id;
		this.content = content;
		this.status = status;
	}

	public TaskId getId() {
		return id;
	}

	public TaskContent getContent() {
		return content;
	}

	public TaskStatus getStatus() {
		return status;
	}
}
