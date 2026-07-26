package com.pawmart.service.impl;

import com.pawmart.entity.Product;
import com.pawmart.entity.ProductImage;
import com.pawmart.exception.AppException;
import com.pawmart.repository.ProductImageRepository;
import com.pawmart.repository.ProductRepository;
import com.pawmart.service.ProductImageService;
import com.pawmart.service.ProductService;
import com.pawmart.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductImageServiceImpl implements ProductImageService {

        private final ProductRepository productRepository;

        private final ProductImageRepository productImageRepository;

        private final UploadService uploadService;


        @Override
        public ProductImage uploadProductImage (Long productId, MultipartFile file){

            // 1. Check product tồn tại
            Product product = productRepository.findById(productId)
                            .orElseThrow(() -> new RuntimeException("Product not found"));
            // 2. Upload cloud
            String imageUrl =uploadService.upload(file);
            // 3. Tạo product image
            ProductImage productImage = ProductImage.builder()
                            .product(product)
                            .imageUrl(imageUrl)
                            .isThumbnail(false)
                            .build();
            // 4. Save database
            return productImageRepository.save(productImage);
        }

    @Override
    public void setThumbnail(Long productId, Long imageId) {

        ProductImage selectedImage = productImageRepository.findByIdAndProductId(imageId, productId);

        if (selectedImage == null) {
            throw new AppException(HttpStatus.NOT_FOUND, "Image not found");
        }

        List<ProductImage> images = productImageRepository.findByProductId(productId);

        for (ProductImage image : images) {
            image.setIsThumbnail(false);
        }

        selectedImage.setIsThumbnail(true);

        productImageRepository.saveAll(images);
    }

    @Override
    public void deleteImage(Long productId, Long imageId) {

        ProductImage image = productImageRepository
                .findByIdAndProductId(imageId, productId);

        if (image == null) {
            throw new AppException(HttpStatus.NOT_FOUND, "Image not found");
        }

        uploadService.delete(image.getImageUrl());

        productImageRepository.delete(image);
    }

    @Override
    public void uploadImages(Long productId, List<MultipartFile> files) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new AppException(HttpStatus.NOT_FOUND, "Product not found"));

        for (MultipartFile file : files) {

            String imageUrl = uploadService.upload(file);

            ProductImage image = new ProductImage();

            image.setProduct(product);
            image.setImageUrl(imageUrl);
            image.setIsThumbnail(false);

            productImageRepository.save(image);
        }
    }
}
