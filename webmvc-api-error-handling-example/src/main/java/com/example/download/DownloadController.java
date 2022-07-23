package com.example.download;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@Controller
@RequestMapping("/download")
public class DownloadController {

	@GetMapping
	public String index() {
		return "download/index";
	}

	@GetMapping(params = "file1")
	@ResponseBody
	public ResponseEntity<StreamingResponseBody> file1() {
		StreamingResponseBody body = out -> out.write("file1".getBytes());
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION,
						//RFC 6266形式のfilename*パラメーターを使う
						"attachment; filename*=utf-8''" + URLEncoder.encode("ファイル1.txt", StandardCharsets.UTF_8))
				.body(body);
	}
}
