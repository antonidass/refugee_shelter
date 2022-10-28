package com.example.refugeeshelter.exceptions;

import com.example.refugeeshelter.dto.ApiResponse;

public class ForbiddenException extends RuntimeException {

    private transient ApiResponse apiResponse;

    private String resourceName;
    private String fieldName;
    private Object fieldValue;

    public ForbiddenException() {
        super();
        this.apiResponse = new ApiResponse(Boolean.FALSE, "You have not authorities for this resource!");
    }

    public ApiResponse getApiResponse() {
        return apiResponse;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }

}
