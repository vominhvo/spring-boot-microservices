package com.vmv.services.product.core.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@Entity
public class Product extends BaseEntity<String> {
    private static final long serialVersionUID = -227264951080660124L;

    @Id
    @Column(unique = true)
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String title;
    private BigDecimal sellingPrice;
    private int subCategoryId;
    private String image;
    private String tags;
}
