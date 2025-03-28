package org.example.jobsearch_51.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.jobsearch_51.service.FileService;
import org.example.jobsearch_51.util.FileValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("images")
@RequiredArgsConstructor
@Validated
public class ImageController {
    private final FileService fileService;
    private final FileValidator fileValidator;

    @GetMapping("{imageName}")
    public ResponseEntity<?> getImage(@PathVariable String imageName) {
        log.info("Requesting image with name: {}", imageName);
        return fileService.findByName(imageName);
    }

    @PostMapping
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        log.info("Uploading image: {}", file.getOriginalFilename());

        fileValidator.validateImageFile(file);

        String filename = fileService.saveImage(file);
        log.info("Image uploaded successfully: {}", filename);
        return filename;
    }
}