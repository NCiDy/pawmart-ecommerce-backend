package com.pawmart.service;

import com.pawmart.entity.ProductImage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductImageService {
    ProductImage uploadProductImage(
            Long productId,
            MultipartFile file
    );
    void setThumbnail(Long productId, Long imageId);

    void deleteImage(Long productId, Long imageId);

    void uploadImages(Long productId, List<MultipartFile> files);
}
