package com.vmv.services.product.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
public class ProductDto {
    private int id;
    private int categoryId;
    private int subCategoryId;
    private String sku;
    private String title;
    private BigDecimal sellingPrice;
    private BigDecimal originalPrice;
    private BigDecimal discount;
    private String description;
    private String image;
    private int stock;
    private String tags;
}
