package com.vmv.services.product.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
public class ProductDto {
    private String id;
    private int type;
    private String name;
    private String image;
    private List<String> tags;
}
