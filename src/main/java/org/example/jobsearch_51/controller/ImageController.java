package org.example.jobsearch_51.controller;

import lombok.RequiredArgsConstructor;
import org.example.jobsearch_51.service.FileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("images")
@RequiredArgsConstructor
public class ImageController {
    private final FileService fileService;

    @GetMapping("{imageName}")
    public ResponseEntity<?> getImage(@PathVariable String imageName) {
        return fileService.findByName(imageName);
    }

    @PostMapping
    public String uploadImage(MultipartFile file) {
        return fileService.saveImage(file);
    }
}