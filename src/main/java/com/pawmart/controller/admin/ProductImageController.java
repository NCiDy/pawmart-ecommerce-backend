package com.pawmart.controller.admin;

import com.pawmart.entity.ProductImage;
import com.pawmart.service.ProductImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class ProductImageController {
    private final ProductImageService productImageService;

    @PostMapping("/{id}/images")
    public ResponseEntity<ProductImage> uploadImage(@PathVariable Long id,@RequestParam("file") MultipartFile file){
        ProductImage image =productImageService.uploadProductImage(id,file);
        return ResponseEntity.ok(image);
    }

    @PutMapping("/{productId}/images/{imageId}/thumbnail")
    public ResponseEntity<Void> setThumbnail(@PathVariable Long productId,@PathVariable Long imageId) {
        productImageService.setThumbnail(productId, imageId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{productId}/images/{imageId}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long productId,@PathVariable Long imageId) {
        productImageService.deleteImage(productId, imageId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{productId}/images/multiple")
    public ResponseEntity<Void> uploadMultiple(@PathVariable Long productId,@RequestParam("files") List<MultipartFile> files) {
        productImageService.uploadImages(productId, files);
        return ResponseEntity.ok().build();
    }
}
