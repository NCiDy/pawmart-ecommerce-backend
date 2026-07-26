package com.pawmart.service.impl;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.pawmart.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UploadServiceImpl implements UploadService {
    private final Cloudinary cloudinary;

    @Override
    public String upload(MultipartFile file) {

        try {

            Map uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.emptyMap()
            );

            return uploadResult.get("secure_url").toString();

        } catch (IOException e) {
            throw new RuntimeException("Upload image failed");
        }
    }

    private String extractPublicId(String imageUrl) {

        String[] parts = imageUrl.split("/");

        String fileName = parts[parts.length - 1];

        return fileName.substring(0, fileName.lastIndexOf("."));
    }

    @Override
    public void delete(String imageUrl) {
        try {
            String publicId = extractPublicId(imageUrl);
            Map result = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            if (!"ok".equals(result.get("result"))) {
                throw new RuntimeException("Delete image failed");
            }
        } catch (Exception e) {
            throw new RuntimeException("Delete image failed", e);
        }
    }
}
