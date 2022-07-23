package com.example.upload;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/upload")
public class UploadController {

	@GetMapping
	public String index() {
		return "upload/index";
	}

	@PostMapping(params = "singleFile")
	public String uploadSingleFile(
			@RequestPart MultipartFile file,
			RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("message", "単一ファイルのアップロード");
		redirectAttributes.addFlashAttribute("uploadedFiles", List.of(file.getOriginalFilename()));
		return "redirect:/upload";
	}

	@PostMapping(params = "multiFiles")
	public String uploadMultiFiles(
			@RequestPart List<MultipartFile> files,
			RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("message", "複数ファイルのアップロード");
		redirectAttributes.addFlashAttribute("uploadedFiles",
				files.stream().map(a -> a.getOriginalFilename()).toList());
		return "redirect:/upload";
	}

	@PostMapping(params = "singleFileWithModel")
	public String uploadSingleFileWithModel(
			@Validated SingleFileForm singleFileForm,
			BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return "upload/index";
		}
		redirectAttributes.addFlashAttribute("message", "単一ファイルのアップロード(モデルへマッピング)");
		redirectAttributes.addFlashAttribute("uploadedFiles", List.of(singleFileForm.getFile().getOriginalFilename()));
		return "redirect:/upload";
	}

	@ModelAttribute
	public SingleFileForm singleFileForm() {
		return new SingleFileForm();
	}

	@PostMapping(params = "multiFilesWithModel")
	public String uploadMultiFilesWithModel(
			@Validated MultiFilesForm multiFilesForm,
			BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return "upload/index";
		}
		redirectAttributes.addFlashAttribute("message", "複数ファイルのアップロード(モデルへマッピング)");
		redirectAttributes.addFlashAttribute("uploadedFiles",
				multiFilesForm.getFiles().stream().map(a -> a.getOriginalFilename()).toList());
		return "redirect:/upload";
	}

	@ModelAttribute
	public MultiFilesForm multiFilesForm() {
		return new MultiFilesForm();
	}
}
