package com.example.valueobject.value;

public class TaskId {

	private final Integer value;

	public TaskId(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public static TaskId valueOf(String value) {
		return new TaskId(Integer.parseInt(value));
	}
}
