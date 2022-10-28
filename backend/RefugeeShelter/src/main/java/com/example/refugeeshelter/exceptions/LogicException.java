package com.example.refugeeshelter.exceptions;

import com.example.refugeeshelter.dto.ApiResponse;

public class LogicException extends RuntimeException {
    private transient ApiResponse apiResponse;


    public LogicException() {
        super();
        String message = String.format("Error in date logic please check...");
        this.apiResponse = new ApiResponse(Boolean.FALSE, message);
    }

    public ApiResponse getApiResponse() {
        return apiResponse;
    }
}
