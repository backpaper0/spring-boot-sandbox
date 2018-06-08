package com.example.resttemplateexample;

import java.io.IOException;
import java.io.UncheckedIOException;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/upload")
public class FileUploaderController {

    @GetMapping
    public String index() {
        return "upload";
    }

    @PostMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String post(@RequestParam final MultipartFile hoge) {
        System.out.printf("name=%s%n", hoge.getName());
        System.out.printf("filename=%s%n", hoge.getOriginalFilename());
        System.out.printf("content-type=%s%n", hoge.getContentType());
        try {
            return new String(hoge.getBytes());
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
