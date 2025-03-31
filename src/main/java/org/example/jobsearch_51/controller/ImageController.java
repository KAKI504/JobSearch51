package org.example.jobsearch_51.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.jobsearch_51.dto.FileDto;
import org.example.jobsearch_51.exceptions.FileProcessingException;
import org.example.jobsearch_51.service.FileService;
import org.example.jobsearch_51.util.FileValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("images")
@RequiredArgsConstructor
public class ImageController {
    private final FileService fileService;
    private final FileValidator fileValidator;

    @GetMapping("{imageName}")
    public ResponseEntity<?> getImageByName(@PathVariable String imageName) {
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

    @PostMapping("/entity")
    public String uploadImageWithEntity(@ModelAttribute FileDto fileDto) {
        log.info("Uploading image with entity ID: {}", fileDto.getEntityId());

        MultipartFile file = fileDto.getFile();
        if (file == null || file.isEmpty()) {
            log.error("File is empty or null");
            throw new FileProcessingException("Файл не может быть пустым");
        }

        fileValidator.validateImageFile(file);

        log.info("File name: {}, size: {}", file.getOriginalFilename(), file.getSize());
        return fileService.saveImage(file);
    }
}