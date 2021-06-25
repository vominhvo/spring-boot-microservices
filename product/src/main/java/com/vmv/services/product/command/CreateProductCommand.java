package com.vmv.services.product.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import java.math.BigDecimal;

@Builder
@Data
public class CreateProductCommand {
    @TargetAggregateIdentifier
    private final String id;
    private final String title;
    private final BigDecimal sellingPrice;
    private final int subCategoryId;
    private final String image;
    private final String tags;
}
