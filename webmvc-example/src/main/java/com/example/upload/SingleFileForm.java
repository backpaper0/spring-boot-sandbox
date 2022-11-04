package com.example.upload;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.AssertTrue;

public class SingleFileForm {

	private MultipartFile file;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	@AssertTrue
	public boolean isEmpty() {
		return file != null && !file.isEmpty();
	}
}
