package com.vmv.services.product.dto.request;

import lombok.Data;
import java.math.BigDecimal;


@Data
public class ProductCreationRequest {
    private String title;
    private BigDecimal sellingPrice;
    private int subCategoryId;
    private String image;
    private String tags;
}