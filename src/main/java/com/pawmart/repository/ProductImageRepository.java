package com.pawmart.repository;

import com.pawmart.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage,Long> {

    List<ProductImage> findByProductId(Long productId);

    ProductImage findByIdAndProductId(Long imageId, Long productId);

}
