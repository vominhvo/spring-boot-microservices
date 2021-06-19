package com.vmv.services.product.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ResponseData implements Serializable {
    private Object data;
    private String message;
    private String code;
    private ResponseStatus status;

    public void setSuccess(Object data) {
        this.status = ResponseStatus.SUCCESS;
        this.data = data;
    }

    public void setFailure(String message, String code) {
        this.status = ResponseStatus.FAILURE;
        this.message = message;
        this.code = code;
    }
}
