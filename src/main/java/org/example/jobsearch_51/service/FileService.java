package org.example.jobsearch_51.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String saveImage(MultipartFile file);
    ResponseEntity<?> findByName(String imageName);
}