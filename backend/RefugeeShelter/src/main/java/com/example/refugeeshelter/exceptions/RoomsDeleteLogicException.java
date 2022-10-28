package com.example.refugeeshelter.exceptions;

import com.example.refugeeshelter.dto.ApiResponse;

public class RoomsDeleteLogicException extends RuntimeException {
    private transient ApiResponse apiResponse;

    private String fieldValue;

    public RoomsDeleteLogicException(String fieldValue) {
        super();
        this.fieldValue = fieldValue;
        String message = String.format("Cannot delete room with id = %s, because this room was reserved", fieldValue);
        this.apiResponse = new ApiResponse(Boolean.FALSE, message);
    }

    public ApiResponse getApiResponse() {
        return apiResponse;
    }

    public Object getFieldValue() {
        return fieldValue;
    }

}
