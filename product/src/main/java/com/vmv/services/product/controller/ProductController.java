package com.vmv.services.product.controller;

import com.vmv.services.product.command.CreateProductCommand;
import com.vmv.services.product.dto.ProductDto;
import com.vmv.services.product.dto.request.ProductCreationRequest;
import com.vmv.services.product.dto.request.ProductUpdateRequest;
import com.vmv.services.product.dto.wrapper.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
// http://localhost:8080/v1/products
@RequestMapping("v1/products")
public class ProductController extends AbstractController {

    final CommandGateway commandGateway;

    @GetMapping(value = "/{id}")
    public ResponseEntity getProduct(@PathVariable String id) {
        var productDto = ProductDto.builder().build();

        var response = new ProductResponse();
        response.setProduct(productDto);
        return setSuccess(HttpStatus.OK, response);
    }

    @PostMapping()
    public ResponseEntity createProduct(@RequestBody ProductCreationRequest request) {
        CreateProductCommand creationCommand = CreateProductCommand.builder()
                .title(request.getTitle())
                .sellingPrice(request.getSellingPrice())
                .subCategoryId(request.getSubCategoryId())
                .image(request.getImage())
                .tags(request.getTags())
                .id(UUID.randomUUID().toString())
                .build();

        String returnValue;
        try {
            returnValue = commandGateway.sendAndWait(creationCommand);
        } catch (Exception ex) {
            returnValue = ex.getLocalizedMessage();
        }

//        var productDto = ProductDto.builder().build();
//
//        var response = new ProductResponse();
//        response.setProduct(productDto);

        return setSuccess(HttpStatus.CREATED, returnValue);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity updateProduct(@PathVariable String id, @RequestBody ProductUpdateRequest request) {
        var productDto = ProductDto.builder().build();

        var response = new ProductResponse();
        response.setProduct(productDto);
        return setSuccess(HttpStatus.OK, response);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteProduct(@PathVariable String id) {
        var productDto = ProductDto.builder().build();

        var response = new ProductResponse();
        response.setProduct(productDto);
        return setSuccess(HttpStatus.NO_CONTENT, response);
    }
}
