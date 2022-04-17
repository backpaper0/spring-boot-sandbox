package com.example.db2db;

public class Demo2 {

	private Integer id;
	private String status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Demo2 [id=" + id + ", status=" + status + "]";
	}
}
