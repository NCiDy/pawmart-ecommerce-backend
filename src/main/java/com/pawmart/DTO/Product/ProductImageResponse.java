package com.pawmart.DTO.Product;

import lombok.*;

@Getter
@Setter
public class ProductImageResponse {

    private Long id;

    private String imageUrl;

    private Boolean isThumbnail;
}
