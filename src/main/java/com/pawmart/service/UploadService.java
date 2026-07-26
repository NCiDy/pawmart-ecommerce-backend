package com.pawmart.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    String upload(MultipartFile file);
    void delete(String imageUrl);
}
