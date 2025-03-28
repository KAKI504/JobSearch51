package org.example.jobsearch_51.util;

import lombok.extern.slf4j.Slf4j;
import org.example.jobsearch_51.exceptions.FileProcessingException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class FileValidator {
    private static final List<String> ALLOWED_IMAGE_TYPES = Arrays.asList(
            "image/jpeg", "image/png", "image/gif", "image/jpg"
    );

    private static final long MAX_FILE_SIZE = 2 * 1024 * 1024;

    public void validateImageFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            log.error("Файл не может быть пустым");
            throw new FileProcessingException("Файл не может быть пустым");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            log.error("Размер файла превышает 2MB: {} bytes", file.getSize());
            throw new FileProcessingException("Размер файла превышает 2MB");
        }

        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_IMAGE_TYPES.contains(contentType.toLowerCase())) {
            log.error("Недопустимый тип файла: {}", contentType);
            throw new FileProcessingException("Допустимы только изображения форматов: JPEG, PNG, GIF");
        }

        log.debug("Файл успешно прошел валидацию: {}, тип: {}, размер: {} bytes",
                file.getOriginalFilename(), contentType, file.getSize());
    }
}