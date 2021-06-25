package com.vmv.services.product.core.event;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductCreatedEvent {
    private String id;
    private String title;
    private BigDecimal sellingPrice;
    private int subCategoryId;
    private String image;
    private String tags;
}
