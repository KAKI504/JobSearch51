package org.example.jobsearch_51.service.impl;

import org.example.jobsearch_51.service.FileService;
import org.example.jobsearch_51.util.FileUtil;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String saveImage(MultipartFile file) {
        FileUtil fu = new FileUtil();
        return fu.saveUploadFile(file, "images/");
    }

    @Override
    public ResponseEntity<?> findByName(String imageName) {
        return new FileUtil().getOutputFile(imageName, "images/", MediaType.IMAGE_JPEG);
    }
}