package com.example.webmvc.model;

public class ModelExampleModel {

	private String text;

	public ModelExampleModel() {
	}

	public ModelExampleModel(final String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ModelExampleModel other = (ModelExampleModel) obj;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ModelExampleModel [text=" + text + "]";
	}
}
