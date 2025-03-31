package org.example.jobsearch_51.service.impl;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.example.jobsearch_51.exceptions.FileProcessingException;
import org.example.jobsearch_51.service.FileService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Slf4j
@Service
public class FileServiceImpl implements FileService {
    private static final String UPLOAD_DIR = "data/";

    @Override
    @SneakyThrows
    public String saveImage(MultipartFile file) {
        log.info("Saving image: {}", file.getOriginalFilename());

        String uuidFile = UUID.randomUUID().toString();
        String resultFileName = uuidFile + "_" + file.getOriginalFilename();
        log.info("Generated file name: {}", resultFileName);

        Path pathDir = Paths.get(UPLOAD_DIR + "images/");
        Files.createDirectories(pathDir);
        log.info("Directory path: {}", pathDir.toAbsolutePath());

        Path filePath = pathDir.resolve(resultFileName);
        log.info("File path: {}", filePath.toAbsolutePath());

        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            log.info("File saved successfully: {}", resultFileName);
        } catch (IOException e) {
            log.error("Error saving file: {}", e.getMessage(), e);
            throw new FileProcessingException("Error saving file: " + e.getMessage());
        }

        return resultFileName;
    }

    @Override
    @SneakyThrows
    public ResponseEntity<?> findByName(String imageName) {
        Path imagePath = Paths.get(UPLOAD_DIR + "images/" + imageName);
        log.info("Looking for image at path: {}", imagePath.toAbsolutePath());

        try {
            byte[] imageData = Files.readAllBytes(imagePath);
            Resource resource = new ByteArrayResource(imageData);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + imageName + "\"")
                    .contentLength(resource.contentLength())
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resource);
        } catch (NoSuchFileException e) {
            log.error("Image not found: {}", imageName, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Image not found: " + imageName);
        } catch (IOException e) {
            log.error("Error reading image: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error reading image: " + e.getMessage());
        }
    }
}