package com.vmv.services.product.controller;

import com.vmv.services.product.dto.ResponseData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class AbstractController {

    ResponseEntity setSuccess(HttpStatus httpStatus, Object data) {
        ResponseData body = new ResponseData();
        body.setSuccess(data);
        return ResponseEntity.status(httpStatus).body(body);
    }

    ResponseEntity setFailure(HttpStatus httpStatus, String message, String code) {
        ResponseData body = new ResponseData();
        body.setFailure(message, code);
        return ResponseEntity.status(httpStatus).body(body);
    }
}
