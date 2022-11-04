package com.example.jdbc;

import java.util.Objects;

public class MyMessage {

	private Long id;
	private String content;
	private boolean sent;

	public MyMessage() {
	}

	public MyMessage(final Long id, final String content, final boolean sent) {
		this.id = id;
		this.content = content;
		this.sent = sent;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, content, sent);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else if (getClass() != obj.getClass()) {
			return false;
		}
		final MyMessage other = (MyMessage) obj;
		return Objects.equals(id, other.id)
				&& Objects.equals(content, other.content)
				&& sent == other.sent;
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(final String content) {
		this.content = content;
	}

	public boolean isSent() {
		return sent;
	}

	public void setSent(final boolean sent) {
		this.sent = sent;
	}
}
