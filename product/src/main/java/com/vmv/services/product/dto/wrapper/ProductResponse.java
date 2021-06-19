package com.vmv.services.product.dto.wrapper;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.vmv.services.product.dto.ProductDto;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Setter
@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ProductResponse implements Serializable {
    private ProductDto product;
}