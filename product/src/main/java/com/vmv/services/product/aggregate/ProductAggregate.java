package com.vmv.services.product.aggregate;

import com.vmv.services.product.command.CreateProductCommand;
import com.vmv.services.product.core.event.ProductCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ObjectUtils;
import java.math.BigDecimal;

@Aggregate
public class ProductAggregate {
    @AggregateIdentifier
    private String id;
    private String title;
    private BigDecimal sellingPrice;
    private int subCategoryId;
    private String image;
    private String tags;

    public ProductAggregate() {

    }

    @CommandHandler
    public ProductAggregate(CreateProductCommand createProductCommand) {
        // Validate the CreateProductCommand
        if (createProductCommand.getSellingPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Selling price cannot be less or equal than zero");
        }

        if (ObjectUtils.isEmpty(createProductCommand.getTitle())) {
            throw new IllegalArgumentException("Title cannot be empty");
        }

        // Apply and publish the Created Event
        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent();

        // Copy the values of which the same properties from source to destination object
        BeanUtils.copyProperties(createProductCommand, productCreatedEvent);

        AggregateLifecycle.apply(productCreatedEvent);
    }

    @EventSourcingHandler
    public void on(ProductCreatedEvent productCreatedEvent) {
        this.id = productCreatedEvent.getId();
        this.title = productCreatedEvent.getTitle();
        this.sellingPrice = productCreatedEvent.getSellingPrice();
        this.subCategoryId = productCreatedEvent.getSubCategoryId();
        this.image = productCreatedEvent.getImage();
        this.tags = productCreatedEvent.getTags();
    }
}
