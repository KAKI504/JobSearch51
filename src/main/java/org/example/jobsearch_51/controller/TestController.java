package org.example.jobsearch_51.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @PostMapping("/upload")
    public String testUpload(@RequestPart(value = "file", required = false) MultipartFile file) {
        if (file == null) {
            log.info("File is null");
            return "File is null";
        }
        log.info("File name: {}", file.getOriginalFilename());
        log.info("File size: {}", file.getSize());
        return "File received: " + file.getOriginalFilename();
    }
}