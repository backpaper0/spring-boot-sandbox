package com.example.upload;

import java.util.List;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

public class MultiFilesForm {

	@Size(min = 1)
	private List<MultipartFile> files = List.of();

	public List<MultipartFile> getFiles() {
		return files;
	}

	public void setFiles(List<MultipartFile> files) {
		this.files = files;
	}

	@AssertTrue
	public boolean isEmpty() {
		return files.size() > 0 && files.stream().allMatch(a -> a != null && !a.isEmpty());
	}
}
