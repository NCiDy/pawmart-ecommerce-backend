package com.pawmart.DTO.Category;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryResponse {

    private Long id;

    private String name;

    private String description;

    private String image;

    private String status;

}
